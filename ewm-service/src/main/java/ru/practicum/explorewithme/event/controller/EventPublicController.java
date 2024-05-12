package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.StatisticClient;
import ru.practicum.explorewithme.StatisticInDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.EventUserParam;
import ru.practicum.explorewithme.event.service.EventService;
import ru.practicum.explorewithme.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.explorewithme.constant.Constant.SERVICE_ID;
import static ru.practicum.explorewithme.constant.Constant.TIME_FORMAT;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {

    private final EventService eventService;
    private final StatisticClient statisticClient;

    //Public endpoints
    @GetMapping("/events")
    public List<EventShortDto> findEventsByPublic(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Long> categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = TIME_FORMAT) LocalDateTime rangeStart,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = TIME_FORMAT) LocalDateTime rangeEnd,
                                                  @RequestParam(required = false) Boolean onlyAvailable,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(required = false, defaultValue = "0") Integer from,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  HttpServletRequest request) {
        log.info("Получен запрос GET /events c параметрами: from = {}, size = {}", from, size);
        EventUserParam eventUserParam = new EventUserParam(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
        StatisticInDto statisticInDto = new StatisticInDto(SERVICE_ID, request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now());
        statisticClient.postHit(statisticInDto);
        List<EventShortDto> events = eventService.findEventsByPublic(eventUserParam, request);
        log.info("Получены события: {}", events);
        return events;
    }

    @GetMapping("/events/{id}")
    public EventFullDto findPublishedEventById(@PathVariable Long id,
                                               HttpServletRequest request) {
        log.info("Получен запрос GET /events/{id} c параметрами: id = {}", id);
        StatisticInDto statisticInDto = new StatisticInDto(SERVICE_ID, request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now());
        statisticClient.postHit(statisticInDto);
        EventFullDto event = eventService.findPublishedEventById(id, request);
        log.info("Получен событие: {}", event);
        return event;
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> findUserEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос GET /users/{userId}/events/{eventId}/requests c параметрами: userId = {}, eventId = {}", userId, eventId);
        List<ParticipationRequestDto> requests = eventService.findUserEventRequests(userId, eventId);
        log.info("Получены запросы: {}", requests);
        return requests;
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeEventRequestsStatus(@PathVariable Long userId,
                                                                    @PathVariable Long eventId,
                                                                    @Valid @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        log.info("Получен запрос PATCH /users/{userId}/events/{eventId}/requests c параметрами: userId = {}, eventId = {}, updateRequest = {}", userId, eventId, updateRequest);
        EventRequestStatusUpdateResult result = eventService.changeEventRequestsStatus(userId, eventId, updateRequest);
        log.info("Обновлены запросы: {}", result);
        return result;
    }

    @GetMapping("/users/{userId}/followers/{followerId}/events")
    public List<EventFullDto> findEventsBySubscriptionOfUser(@PathVariable Long userId,
                                                             @PathVariable Long followerId,
                                                             @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                                             @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        return eventService.findEventsBySubscriptionOfUser(userId, followerId, from, size);
    }

    @GetMapping("/users/followers/{followerId}/events")
    public List<EventShortDto> findEventsByAllSubscriptions(@PathVariable Long followerId,
                                                            @RequestParam(required = false, defaultValue = "NEW") String sort,
                                                            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                                            @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        return eventService.findEventsByAllSubscriptions(followerId, sort, from, size);
    }
}
