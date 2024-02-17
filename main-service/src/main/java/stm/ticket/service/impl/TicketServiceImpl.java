package stm.ticket.service.impl;

import dto.TicketSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import stm.exception.model.BadRequestException;
import stm.exception.model.ResourceNotFoundException;
import stm.kafka.KafkaProducer;
import stm.ticket.dto.TicketDto;
import stm.ticket.mapper.TicketMapper;
import stm.ticket.model.Ticket;
import stm.ticket.service.TicketService;
import stm.ticket.storage.TicketRepository;
import stm.user.model.User;
import stm.user.storage.UserRepository;
import stm.util.Constants;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    String CASH_USER_TICKETS = "cashUserTickets::";
    Duration cashDuretion = Duration.ofMinutes(10);
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    private final RedisTemplate<String, List<TicketDto>> redisTemplate;

    private final KafkaProducer kafkaProducer;

    private final UserRepository userRepository;

    @Override
    public List<TicketDto> getAllTickets(LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Integer departurePointId, String departurePoint,
                                         Integer destinationPointId, String destinationPoint,
                                         String carrier, Integer from, Integer size) {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
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

    @Override
    public TicketDto buyTicket(Integer userId, Integer ticketId) {
        Ticket ticket;
        User user;
        try {
            ticket = ticketRepository.getTicket(ticketId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("билет не найден либо продан");
        }
        try {
            user = userRepository.getById(userId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("покупатель с id = " + userId + " не найден");
        }
        ticketRepository.ticketSetUser(ticketId, userId);
        TicketDto ticketDto = ticketMapper.toTicketDto(ticket);
        List<TicketDto> list = new ArrayList<>();
        try {
            list.addAll(Objects.requireNonNull(redisTemplate.opsForValue().get(CASH_USER_TICKETS + userId)));
        } catch (NullPointerException ignored) {
        }
        list.add(ticketDto);
        redisTemplate.opsForValue().set(CASH_USER_TICKETS + userId, list, cashDuretion);
        kafkaProducer.send(TicketSaveDto.builder()
                .id((long) ticket.getId())
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

    @Override
    public List<TicketDto> getUserTickets(Integer id) {
        List<TicketDto> list;
        try {
            list = new ArrayList<>(Objects.requireNonNull(redisTemplate.opsForValue().get(CASH_USER_TICKETS + id)));
            return list;
        } catch (NullPointerException ignored) {
        }
        try {
            list = ticketMapper.toTicketDtoList(ticketRepository.getUserTickets(id));
            redisTemplate.opsForValue().set(CASH_USER_TICKETS + id, list, cashDuretion);
            return list;
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("у пользователя нет купленных билетов");
        }
    }
}
