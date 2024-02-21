package stm.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stm.exception.model.ResourceNotFoundException;
import stm.user.model.User;
import stm.user.service.UserService;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user;
        try {
            user = userService.getByLogin(username);
        } catch (UsernameNotFoundException e) {
            throw new ResourceNotFoundException("пользователь не найден");
        }

        return JwtEntityFactory.create(user);
    }
}
