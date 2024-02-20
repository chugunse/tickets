package stm.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.route.dto.RoutePublicDto;

@NoArgsConstructor
@Setter
@Getter
@Schema(description = "рейс")
public class TripPublicDto {
    @Schema(description = "название рейса")
    private String title;
    @Schema(description = "маршрут")
    private RoutePublicDto route;
    @Schema(description = "время рейса",example = "YYYY-MM-dd HH:mm")
    private String dateTime;
    @Schema(description = "цена билета")
    private int price;
    @Schema(description = "количество свободных мест")
    private int available;
}