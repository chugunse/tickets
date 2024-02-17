package stm.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    private final String login;
    private final String password;
    private String fullName;
}
