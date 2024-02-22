package stm.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {
    @NotNull(message = "login не должен быть пуст")
    private String login;
    @NotNull(message = "password не должен быть пуст")
    private String password;
}
