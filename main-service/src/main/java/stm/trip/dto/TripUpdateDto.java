package stm.trip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.util.validator.CastomDataTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
public class TripUpdateDto {
    @Size(min = 3, max = 50, message = "номер рейса слишком длинный или короткий")
    private String title;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}",
            message = "неверный формат даты/времени рейса (YYYY-MM-dd HH:mm)")
    @CastomDataTime(message = "время рейса не может быть раньше," +
            " чем через два часа от текущего момента", delay = 2)
    private String dateTime;
    @Positive
    private int price;
}