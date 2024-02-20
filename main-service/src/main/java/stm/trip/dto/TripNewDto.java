package stm.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.util.validator.CastomDataTime;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "новый рейс")
public class TripNewDto {
    @NotBlank(message = "номер рейса не может быть пустым")
    @Size(min = 3, max = 50, message = "номер рейса слишком длинный или короткий")
    @Schema(description = "название рейса")
    private String title;
    @NotNull
    @Schema(description = "id маршрута")
    private Long route;
    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}",
            message = "неверный формат даты/времени рейса (YYYY-MM-dd HH:mm)")
    @CastomDataTime(message = "время рейса не может быть раньше," +
            " чем через два часа от текущего момента", delay = 2)
    @Schema(description = "время рейса",example = "YYYY-MM-dd HH:mm")
    private String dateTime;
    @Positive
    @NotNull
    @Schema(description = "цена билета")
    private int price;
    @NotNull
    @Positive
    @Schema(description = "количество посадочных мест")
    private int amount;
}
