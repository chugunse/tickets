package stm.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.trip.dto.TripFullDto;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "билет")
public class TicketDto implements Serializable {
    @Schema(description = "номер билета")
    private Long id;
    @Schema(description = "рейс")
    private TripFullDto trip;
    @Schema(description = "номер посадочного места")
    private int placeNumber;
    @Schema(description = "цена билета")
    private int price;
}
