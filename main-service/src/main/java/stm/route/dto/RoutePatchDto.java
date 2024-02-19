package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class RoutePatchDto {
    @Size(min = 3, max = 50, message = "номер маршрута слишком длинный или короткий")
    private String routeNumber;
    private Long departurePoint;
    private Long destinationPoint;
    private Long carrierId;
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "неверный формат времени продолжительности маршрута (HH:mm)")
    private String duration;
}
