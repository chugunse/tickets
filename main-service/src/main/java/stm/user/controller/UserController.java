package stm.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stm.user.dto.UserDto;
import stm.user.dto.UserNewDto;
import stm.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "юзер контроллер", description = "контроллер управления пользователями")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "регистрация пользователя")
    public UserDto add(@Valid @RequestBody UserNewDto user) {
        log.info("post: {}", user);
        return userService.addUser(user);
    }
}
