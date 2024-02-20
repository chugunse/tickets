package stm.route.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "обновленный маршрут")
public class RoutePatchDto {
    @Size(min = 3, max = 50, message = "номер маршрута слишком длинный или короткий")
    @Schema(description = "номер маршрута")
    private String routeNumber;
    @Schema(description = "id точки отправления")
    private Long departurePoint;
    @Schema(description = "id точки назначения")
    private Long destinationPoint;
    @Schema(description = "id перевозчика")
    private Long carrierId;
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "неверный формат времени продолжительности маршрута (HH:mm)")
    @Schema(description = "продолжительность маршрута", example = "HH:mm")
    private String duration;
}
