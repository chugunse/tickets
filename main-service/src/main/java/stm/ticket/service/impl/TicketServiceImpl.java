package stm.ticket.service.impl;

import dto.TicketSaveDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import stm.exception.model.BadRequestException;
import stm.exception.model.ResourceNotFoundException;
import stm.kafka.KafkaProducer;
import stm.ticket.dto.TicketDto;
import stm.ticket.mapper.TicketMapper;
import stm.ticket.model.Ticket;
import stm.ticket.service.TicketService;
import stm.ticket.storage.TicketRepository;
import stm.user.model.User;
import stm.user.service.UserService;
import stm.util.Constants;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {
    String CASH_USER_TICKETS = "cashUserTickets::";
    Duration cashDuretion = Duration.ofMinutes(10);
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    private final RedisTemplate<String, List<TicketDto>> redisTemplate;

    private final KafkaProducer kafkaProducer;
    private final UserService userService;

    @Transactional
    @Override
    public List<TicketDto> getAllTickets(LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Long departurePointId, String departurePoint,
                                         Long destinationPointId, String destinationPoint,
                                         String carrier, Integer from, Integer size) {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                log.warn("ошибка в веденных параметрах запроса времени");
                throw new BadRequestException("ошибка в веденных параметрах запроса времени");
            }
        }
        String rangeStartStr = (rangeStart != null) ? rangeStart.format(Constants.formatterDateTime) : null;
        String rangeEndStr = (rangeEnd != null) ? rangeEnd.format(Constants.formatterDateTime) : null;

        List<Ticket> tickets = ticketRepository.getTickets(rangeStartStr, rangeEndStr,
                departurePointId, destinationPointId,
                departurePoint, destinationPoint,
                carrier, from, size);
        System.out.println(tickets);
        return ticketMapper.toTicketDtoList(tickets);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public TicketDto buyTicket(Long ticketId, HttpServletRequest request) {
        Ticket ticket;
        User user = userService.getByLogin(request.getUserPrincipal().getName());
        try {
            ticket = ticketRepository.getTicket(ticketId);
        } catch (EmptyResultDataAccessException exception) {
            log.warn("билет не найден либо продан");
            throw new ResourceNotFoundException("билет не найден либо продан");
        }
        ticketRepository.ticketSetUser(ticketId, user.getId());
        TicketDto ticketDto = ticketMapper.toTicketDto(ticket);
        List<TicketDto> list = new ArrayList<>();
        try {
            list.addAll(Objects.requireNonNull(redisTemplate.opsForValue().get(CASH_USER_TICKETS + user.getId())));
        } catch (NullPointerException ignored) {
        }
        list.add(ticketDto);
        redisTemplate.opsForValue().set(CASH_USER_TICKETS + user.getId(), list, cashDuretion);
        kafkaProducer.send(TicketSaveDto.builder()
                .id(ticket.getId())
                .buyersFullName(user.getFullName())
                .placeNumber(ticket.getPlaceNumber())
                .tripTitle(ticket.getTrip().getTitle())
                .routeNumber(ticket.getTrip().getRoute().getRouteNumber())
                .departurePoint(ticket.getTrip().getRoute().getDeparturePoint().getTitle())
                .destinationPoint(ticket.getTrip().getRoute().getDestinationPoint().getTitle())
                .dateTime(ticket.getTrip().getDateTime())
                .price(ticket.getTrip().getPrice())
                .carrier(ticket.getTrip().getRoute().getCarrier().getCompany())
                .timestamp(LocalDateTime.now())
                .build());
        return ticketDto;
    }

    @Transactional
    @Override
    public List<TicketDto> getUserTickets(HttpServletRequest request) {
        Long userId = userService.getByLogin(request.getUserPrincipal().getName()).getId();
        List<TicketDto> list;
        try {
            list = new ArrayList<>(Objects.requireNonNull(redisTemplate.opsForValue().get(CASH_USER_TICKETS + userId)));
            return list;
        } catch (NullPointerException ignored) {
        }
        try {
            log.debug("достаем билеты из базы!!!");
            list = ticketMapper.toTicketDtoList(ticketRepository.getUserTickets(userId));
            redisTemplate.opsForValue().set(CASH_USER_TICKETS + userId, list, cashDuretion);
            return list;
        } catch (EmptyResultDataAccessException exception) {
            log.warn("у пользователя нет купленных билетов");
            throw new ResourceNotFoundException("у пользователя нет купленных билетов");
        }
    }
}
