package stm.carrier.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "новый перевозчик")
public class CarrierDto {
    @NotBlank
    @Size(min = 3, max = 50, message = "название компаниие слишком длинное или короткое")
    @Schema(description = "название компании перевозчика")
    String company;
    @NotBlank
    @Size(min = 5, max = 11, message = "некорректный номер телефона")
    @Schema(description = "номер телефона")
    String phone;
}
