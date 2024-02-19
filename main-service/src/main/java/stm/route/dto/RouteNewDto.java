package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class RouteNewDto {
    @NotBlank(message = "номер маршрута не должно быть пустым")
    @Size(min = 3, max = 50, message = "номер маршрута слишком длинный или короткий")
    private String routeNumber;
    @NotNull(message = "номер точки отправления не должен быть пустым")
    private Long departurePoint;
    @NotNull(message = "номер точки назначения не должен быть пустым")
    private Long destinationPoint;
    @NotNull(message = "номер перевозчина не должем быть пустым")
    private Long carrierId;
    @NotNull
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "неверный формат времени продолжительности маршрута (HH:mm)")
    private String duration;
}
