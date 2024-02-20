package stm.route.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "пункт назначения/отправления")
public class PointDto implements Serializable {
    @Schema(description = "id пункта")
    private Long id;
    @NotBlank(message = "название пункта не должно быть пустым")
    @Size(min = 3, max = 50, message = "название пункта слишком длинное или короткое")
    @Schema(description = "название пункта назначения/отправления")
    private String title;
}
