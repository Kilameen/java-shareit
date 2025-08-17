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

/**
 *  Контроллер для обработки запросов, связанных с пользователями.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Validated
@Slf4j
public class UserController {
    private final UserClient userClient;

    /**
     * Обрабатывает POST-запрос на создание нового пользователя.
     * Валидация выполняется для группы Marker.OnCreate.
     * @param userRequestDto DTO с данными пользователя.
     * @return ResponseEntity с результатом создания пользователя.
     */
    @Validated(Marker.OnCreate.class)
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("POST запрос на создание пользователя: {}", userRequestDto);
        return userClient.create(userRequestDto);
    }

    /**
     * Обрабатывает PATCH-запрос на обновление существующего пользователя.
     * Валидация выполняется для группы Marker.OnUpdate.
     * @param userDto DTO с данными пользователя для обновления.
     * @param id ID пользователя, которого необходимо обновить.
     * @return ResponseEntity с результатом обновления пользователя.
     */
    @Validated(Marker.OnUpdate.class)
    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody UserRequestDto userDto, @PathVariable Long id) {
        log.info("PATCH запрос на обновление пользователя c id: {}", id);
        return userClient.update(id, userDto);
    }

    /**
     * Обрабатывает GET-запрос на получение списка всех пользователей.
     * @return ResponseEntity со списком всех пользователей.
     */
    @GetMapping
    public ResponseEntity<Object> findAll() {
        log.info("GET запрос на получение списка всех пользователей.");
        return userClient.findAll();
    }

    /**
     * Обрабатывает GET-запрос на получение пользователя по ID.
     * @param id ID пользователя.
     * @return ResponseEntity с данными пользователя.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        log.info("GET запрос на получение пользователя c id: {}", id);
        return userClient.findById(id);
    }

    /**
     * Обрабатывает DELETE-запрос на удаление пользователя по ID.
     * @param id ID пользователя.
     * @return ResponseEntity с результатом удаления пользователя.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("DELETE запрос на удаление пользователя с id: {}", id);
        return userClient.deleteById(id);
    }
}
