package stm.route.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.carrier.dto.CarrierDto;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "маршрут")
public class RoutePublicDto {
    @Schema(description = "номер маршрута")
    private String routeNumber;
    @Schema(description = "отчка отправления")
    private PointDto departurePoint;
    @Schema(description = "точка назначения")
    private PointDto destinationPoint;
    @Schema(description = "перевозчик")
    private CarrierDto carrier;
    @Schema(description = "продолжительность маршрута", example = "HH:mm")
    private String duration;
}