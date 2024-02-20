package stm.route.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.carrier.dto.CarrierFullDto;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "маршрут")
public class RouteFullDto {
    @Schema(description = "id маршрута")
    private Long id;
    @Schema(description = "номер маршрута")
    private String routeNumber;
    @Schema(description = "отчка отправления")
    private PointDto departurePoint;
    @Schema(description = "точка назначения")
    private PointDto destinationPoint;
    @Schema(description = "перевозчик")
    private CarrierFullDto carrier;
    @Schema(description = "продолжительность маршрута")
    private String duration;
}
