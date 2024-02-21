package stm.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "dto нового пользователя")
public class UserNewDto {
    @NotBlank(message = "логин не должен быть пустым")
    @Size(min = 3, max = 50, message = "логин слишком длинный или короткий")
    String login;
    @NotBlank(message = "пароль не должен быть пустым")
    @Size(min = 6, max = 50, message = "пароль слишком длинный или короткий")
    String password;
    @NotBlank(message = "имя не должно быть пустым")
    @Size(min = 10, max = 50, message = "имя слишком длинное или короткое")
    String fullName;
}
