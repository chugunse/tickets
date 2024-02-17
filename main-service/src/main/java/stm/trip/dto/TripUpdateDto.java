package stm.trip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TripUpdateDto {
    private String title;
    private String dateTime;
    private int price;
}