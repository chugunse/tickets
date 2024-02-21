package stm.carrier.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "перевозчик")
public class CarrierFullDto {
    Long id;
    @NotBlank
    @Size(min = 3, max = 50, message = "название компаниие слишком длинное или короткое")
    @Schema(description = "название компании перевозчика")
    String company;
    @NotBlank
    @Size(min = 5, max = 11, message = "некорректный номер телефона")
    @Schema(description = "номер телефона")
    String phone;
}
