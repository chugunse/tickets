package stm.ticket.storage;

import stm.ticket.model.Ticket;

import java.util.List;

public interface TicketRepository {
    List<Ticket> getTickets(String rangeStart, String rangeEnd,
                            Long departurePointId, Long destinationPointId,
                            String departurePoint, String destinationPoint,
                            String carrier, Integer from, Integer size);

    Ticket getTicket(Long id);

    void ticketSetUser(Long ticketId, Long userId);

    List<Ticket> getUserTickets(Long id);
}
