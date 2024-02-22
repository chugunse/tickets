package stm.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.util.validator.CastomDataTime;

@NoArgsConstructor
@Setter
@Getter
@Schema(description = "обновление иформации рейса")
public class TripUpdateDto {
    @Size(min = 3, max = 50, message = "номер рейса слишком длинный или короткий")
    @Schema(description = "название рейса")
    private String title;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}",
            message = "неверный формат даты/времени рейса (YYYY-MM-dd HH:mm)")
    @CastomDataTime(message = "время рейса не может быть раньше," +
            " чем через два часа от текущего момента", delay = 2)
    @Schema(description = "время рейса", example = "YYYY-MM-dd HH:mm")
    private String dateTime;
    @Positive
    @Schema(description = "цена билета")
    private int price;
}