package ru.practicum.explorewithme.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.user.dto.UserWithFollowersDto;
import ru.practicum.explorewithme.user.service.UserService;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserPrivateController {

    private final UserService userService;

    @PostMapping(value = "/users/{userId}/followers/{followerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserWithFollowersDto addFollower(@PathVariable Long userId, @PathVariable Long followerId) {
        log.info("Получен запрос POST /users/{}/followers/{}", userId, followerId);
        UserWithFollowersDto user = userService.addFollower(userId, followerId);
        log.info("Добавлен подписчик: {}", user);
        return user;
    }

    @DeleteMapping(value = "/users/{userId}/followers/{followerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFollower(@PathVariable Long userId, @PathVariable Long followerId) {
        log.info("Получен запрос DELETE /users/{}/followers/{}", userId, followerId);
        userService.deleteFollower(userId, followerId);
        log.info("Удален подписчик: {}", userId);
    }
}
