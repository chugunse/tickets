package stm.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.trip.dto.TripFullDto;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class TicketDto implements Serializable {
    private Long id;
    private TripFullDto trip;
    private int placeNumber;
    private int price;
}
