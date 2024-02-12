package stm.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.trip.dto.TripFullDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TicketDto {
    private int id;
    private TripFullDto trip;
    private int placeNumber;
    private int price;
}
