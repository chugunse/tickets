package stm.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.route.model.Route;

import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@Schema(description = "рейс")
public class TripFullDto implements Serializable {
    @Schema(description = "id рейса")
    private Long id;
    @Schema(description = "название рейса")
    private String title;
    @Schema(description = "маршрут")
    private Route route;
    @Schema(description = "время рейса",example = "YYYY-MM-dd HH:mm")
    private String dateTime;
    @Schema(description = "цена билета")
    private int price;
    @Schema(description = "количество посадочных мест")
    private int amount;
    @Schema(description = "количество проданных билетов")
    private int sold;
}
