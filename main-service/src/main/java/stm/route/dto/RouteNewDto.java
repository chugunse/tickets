package stm.route.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "новый маршрут")
public class RouteNewDto {
    @NotBlank(message = "номер маршрута не должно быть пустым")
    @Size(min = 3, max = 50, message = "номер маршрута слишком длинный или короткий")
    @Schema(description = "название маршрута")
    private String routeNumber;
    @NotNull(message = "номер точки отправления не должен быть пустым")
    @Schema(description = "id точки отправления")
    private Long departurePoint;
    @NotNull(message = "номер точки назначения не должен быть пустым")
    @Schema(description = "id точки назначения")
    private Long destinationPoint;
    @NotNull(message = "номер перевозчина не должем быть пустым")
    @Schema(description = "id перевозчика")
    private Long carrierId;
    @NotNull
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "неверный формат времени продолжительности маршрута (HH:mm)")
    @Schema(description = "продолжительность маршрута", example = "HH:mm")
    private String duration;
}
