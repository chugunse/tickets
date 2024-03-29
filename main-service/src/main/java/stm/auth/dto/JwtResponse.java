package stm.auth.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private Long id;
    private String login;
    private String accessToken;
    private String refreshToken;
}
