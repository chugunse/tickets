package stm.ticket.storage;

import stm.ticket.dto.TicketDto;
import stm.ticket.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    TicketDto saveTickets(Ticket ticket, int amount);

    List<Ticket> getTickets(String rangeStart, String rangeEnd,
                            Integer departurePointId, Integer destinationPointId,
                            String departurePoint, String destinationPoint,
                            String carrier, Integer from, Integer size);

    Ticket getTicket(Integer id);

    void ticketSetUser(Integer ticketId, Integer userId);

    List<Ticket> getUserTickets(Integer id);
}
