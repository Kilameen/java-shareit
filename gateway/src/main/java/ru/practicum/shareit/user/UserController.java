package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.utils.Marker;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Validated
@Slf4j
public class UserController {
    private final UserClient userClient;

    @Validated(Marker.OnCreate.class)
    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody UserRequestDto user) {
        log.info("POST запрос на создание пользователя: {}", user);
        return userClient.create(user);
    }
    @Validated(Marker.OnUpdate.class)
    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(@Valid @RequestBody UserRequestDto userDto, @PathVariable Long userId) {
        log.info("PATCH запрос на обновление пользователя c id: {}", userId);
        return userClient.update(userId, userDto);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        log.info("GET запрос на получение списка всех пользователей.");
        return userClient.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findById(@PathVariable Long userId) {
        log.info("GET запрос на получение пользователя c id: {}", userId);
        return userClient.findById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable long userId) {
        log.info("DELETE запрос на удаление пользователя с id: {}", userId);
        return userClient.deleteById(userId);
    }
}