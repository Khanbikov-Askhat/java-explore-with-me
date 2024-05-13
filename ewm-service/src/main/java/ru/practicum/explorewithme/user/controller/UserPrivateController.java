package ru.practicum.explorewithme.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.user.dto.UserWithFollowersDto;
import ru.practicum.explorewithme.user.service.UserService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserPrivateController {

    private final UserService userService;

    @PostMapping(value = "/users/{userId}/followers/{followerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserWithFollowersDto addFollower(@PathVariable Long userId, @PathVariable Long followerId) {
        return userService.addFollower(userId, followerId);
    }

    @DeleteMapping(value = "/users/{userId}/followers/{followerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFollower(@PathVariable Long userId, @PathVariable Long followerId) {
        userService.deleteFollower(userId, followerId);
    }
}
