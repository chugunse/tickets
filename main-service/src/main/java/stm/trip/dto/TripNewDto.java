package stm.trip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TripNewDto {
    private String title;
    private Long route;
    private String dateTime;
    private int price;
    private int amount;
}
