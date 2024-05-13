package ru.practicum.explorewithme.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.user.dto.UserInDto;
import ru.practicum.explorewithme.user.dto.UserOutDto;
import ru.practicum.explorewithme.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Slf4j
public class UserAdminController {

    private final UserService userService;

    @GetMapping
    public List<UserOutDto> findUsers(@RequestParam(required = false) List<Long> ids,
                                      @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                      @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Получен запрос GET /admin/users c параметрами: ids = {}, from = {}, size = {}", ids, from, size);
        List<UserOutDto> users = userService.findUsers(ids, from, size);
        log.info("Получены пользователи: {}", users);
        return users;
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@NotNull @PathVariable Long userId) {
        log.info("Получен запрос DELETE /admin/users/{}", userId);
        userService.deleteUser(userId);
        log.info("Удален пользователь: {}", userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutDto addUser(@Valid @RequestBody UserInDto inDto) {
        log.info("Получен запрос POST /admin/users c параметрами: {}", inDto);
        UserOutDto user = userService.addUser(inDto);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }
}
