package stm.carrier.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CarrierFullDto {
    Long id;
    @NotBlank
    @Size(min = 3, max = 50, message = "название компаниие слишком длинное или короткое")
    String company;
    @NotBlank
    @Size(min = 5, max = 11, message = "некорректный номер телефона")
    String phone;
}
