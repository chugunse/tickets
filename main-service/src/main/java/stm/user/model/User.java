package stm.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(hidden = true)
public class User {
    private Long id;
    private final String login;
    private final String password;
    private String fullName;
}
