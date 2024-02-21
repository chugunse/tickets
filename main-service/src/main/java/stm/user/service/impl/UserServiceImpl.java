package stm.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stm.exception.model.ConflictException;
import stm.exception.model.ResourceNotFoundException;
import stm.user.dto.UserDto;
import stm.user.dto.UserNewDto;
import stm.user.mapper.UserMapper;
import stm.user.model.Role;
import stm.user.model.User;
import stm.user.service.UserService;
import stm.user.storage.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto addUser(UserNewDto dto) {
        try {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ROLE_USER);
            User user = userMapper.toUser(dto);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user = userRepository.save(user);
            return userMapper.toUserDto(user);
        } catch (DuplicateKeyException e) {
            log.info("логин {} занят", dto.getLogin()
            );
            throw new ConflictException("логин '" + dto.getLogin() + "' занят");
        }
    }

    @Override
    @Transactional
    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(Long id) {
        try {
            return userRepository.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.warn("user {} не найден", id);
            throw new ResourceNotFoundException("user " + id + " не найден");
        }
    }

    @Override
    @Transactional
//    @Cacheable(value = "UserService::getByLogin", key = "#login")
    public User getByLogin(String login) {
        try {
            return userRepository.getByLogin(login);
        } catch (EmptyResultDataAccessException exception) {
            log.warn("user {} не найден", login);
            throw new ResourceNotFoundException("user " + login + " не найден");
        }
    }
}
