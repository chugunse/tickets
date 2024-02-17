package stm.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import stm.trip.model.Trip;

@Data
@AllArgsConstructor
@Builder
public class Ticket {
    private int id;
    private Trip trip;
    private int placeNumber;
}
