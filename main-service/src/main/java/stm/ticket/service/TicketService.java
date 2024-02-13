package stm.ticket.service;

import stm.ticket.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {

    List<TicketDto> getAllTickets(LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd,
                                  Integer departurePointId, String departurePoint,
                                  Integer destinationPointId, String destinationPoint,
                                  String carrier,
                                  Integer from,
                                  Integer size);

    TicketDto buyTicket(Integer userId, Integer ticketId);

    List<TicketDto> getUserTickets(Integer id);
}
