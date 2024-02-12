package stm.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import stm.route.model.Route;
import stm.trip.model.Trip;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Ticket {
    private int id;
    private Trip trip;
    private int placeNumber;
}
