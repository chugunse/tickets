package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class PointDto implements Serializable {
    private Long id;
    @NotBlank(message = "название пункта не должно быть пустым")
    @Size(min = 3, max = 50, message = "название пункта слишком длинное или короткое")
    private String title;
}
