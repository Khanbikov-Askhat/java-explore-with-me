package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.StatisticClient;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventUserRequest;
import ru.practicum.explorewithme.event.service.EventService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {

    private final EventService eventService;
    private final StatisticClient statisticClient;

    //Private endpoints
    @PostMapping(value = "/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable Long userId, @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос POST /users/{userId}/events c параметрами: userId = {}, newEventDto = {}", userId, newEventDto);
        EventFullDto event = eventService.addEvent(userId, newEventDto);
        log.info("Добавлен событие: {}", event);
        return event;
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> findEventsOfUser(@PathVariable Long userId,
                                                @RequestParam(required = false, defaultValue = "0") Integer from,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Получен запрос GET /users/{userId}/events c параметрами: userId = {}, from = {}, size = {}", userId, from, size);
        List<EventShortDto> events = eventService.findEventsOfUser(userId, from, size);
        log.info("Получены события: {}", events);
        return events;
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto findUserEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос GET /users/{userId}/events/{eventId} c параметрами: userId = {}, eventId = {}", userId, eventId);
        EventFullDto event = eventService.findUserEventById(userId, eventId);
        log.info("Получено событие: {}", event);
        return event;
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto userUpdateEvent(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("Получен запрос PATCH /users/{userId}/events/{eventId} c параметрами: userId = {}, eventId = {}, updateEventUserRequest = {}",
                userId, eventId, updateEventUserRequest);
        EventFullDto event = eventService.userUpdateEvent(userId, eventId, updateEventUserRequest);
        log.info("Обновлено событие: {}", event);
        return event;
    }
}
