package stm.user.service;

import stm.user.dto.UserDto;
import stm.user.dto.UserNewDto;
import stm.user.model.User;

public interface UserService {
    UserDto addUser(UserNewDto dto);
    User getById(Long id);
    User getByLogin(String login);
}
