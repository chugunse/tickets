package stm.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import stm.auth.dto.JwtRequest;
import stm.auth.dto.JwtResponse;
import stm.auth.security.JwtTokenProvider;
import stm.user.model.User;
import stm.user.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(), loginRequest.getPassword())
        );
        User user = userService.getByLogin(loginRequest.getLogin());
        jwtResponse.setId(user.getId());
        jwtResponse.setLogin(user.getLogin());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(), user.getLogin(), user.getRoles())
        );
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                user.getId(), user.getLogin())
        );
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        refreshToken = refreshToken.replaceAll("^\"|\"$", "");
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
