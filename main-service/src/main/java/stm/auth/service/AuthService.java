package stm.auth.service;

import stm.auth.dto.JwtRequest;
import stm.auth.dto.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
