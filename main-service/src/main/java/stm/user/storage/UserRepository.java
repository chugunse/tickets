package stm.user.storage;

import stm.user.model.User;

public interface UserRepository {
    User save(User user);

    User getById(Long userId);

    User getByLogin(String login);
}
