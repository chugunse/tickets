package stm.ticket.service.impl;

import dto.TicketSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
import stm.util.Constants;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    String CASH_USER_TICKETS = "yyyy-MM-dd HH:mm";
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    private final RedisTemplate<String, List<TicketDto>> redisTemplate;

    private final KafkaProducer kafkaProducer;

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
    @CachePut(value = "allTickets" + "#ticketId", key = "#ticketId")
    public TicketDto buyTicket(Integer userId, Integer ticketId) {
        Ticket ticket;
        try {
            ticket = ticketRepository.getTicket(ticketId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("билет не найден либо продан");
        }
        ticketRepository.ticketSetUser(ticketId, userId);
        kafkaProducer.send(TicketSaveDto.builder()
                .id((long) ticket.getId())
                .buyersFullName(userId.toString())
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
        return ticketMapper.toTicketDto(ticket);
    }

    @Override
    @Cacheable(value = "allTickets" + "#id", unless = "#result.size() == 0")
    public List<TicketDto> getUserTickets(Integer id) {
        try {
            System.out.println(LocalDateTime.now());
            return ticketMapper.toTicketDtoList(ticketRepository.getUserTickets(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("у пользователя нет купленных билетов");
        }
    }
}
