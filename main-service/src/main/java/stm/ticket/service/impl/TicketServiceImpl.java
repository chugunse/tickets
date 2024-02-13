package stm.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import stm.exception.model.BadRequestException;
import stm.exception.model.ResourceNotFoundException;
import stm.route.storage.RouteRepository;
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
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

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
        try {
            ticket = ticketRepository.getTicket(ticketId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("билет не найден либо продан");
        }
        ticketRepository.ticketSetUser(ticketId, userId);
        return ticketMapper.toTicketDto(ticket);
    }

    @Override
    public List<TicketDto> getUserTickets(Integer id) {
        try {
            return ticketMapper.toTicketDtoList(ticketRepository.getUserTickets(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("у пользователя нет купленных билетов");
        }
    }
}
