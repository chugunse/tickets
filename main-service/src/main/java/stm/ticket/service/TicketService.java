package stm.ticket.service;

import jakarta.servlet.http.HttpServletRequest;
import stm.ticket.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {

    List<TicketDto> getAllTickets(LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd,
                                  Long departurePointId, String departurePoint,
                                  Long destinationPointId, String destinationPoint,
                                  String carrier,
                                  Integer from,
                                  Integer size);

    TicketDto buyTicket(Long ticketId, HttpServletRequest request);

    List<TicketDto> getUserTickets(HttpServletRequest request);
}
