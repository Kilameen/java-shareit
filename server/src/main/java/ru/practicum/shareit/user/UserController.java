package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с пользователями.
 * Обрабатывает CRUD операции для пользователей.
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Создает нового пользователя.
     * @param userDto DTO с данными нового пользователя.
     * @return DTO созданного пользователя.
     */
    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {
        log.info("POST запрос на создание пользователя: {}", userDto);
        return userService.create(userDto);
    }

    /**
     * Обновляет существующего пользователя.
     * @param id ID пользователя, которого нужно обновить.
     * @param userDto DTO с данными для обновления пользователя.
     * @return DTO обновленного пользователя.
     */
    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        log.info("PATCH запрос на обновление пользователя c id: {}", id);
        return userService.update(id, userDto);
    }

    /**
     * Получает пользователя по его ID.
     * @param id ID пользователя.
     * @return DTO пользователя.
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        log.info("GET запрос на получение пользователя c id: {}", id);
        return userService.findUserById(id);
    }

    /**
     * Удаляет пользователя по его ID.
     * @param id ID пользователя, которого нужно удалить.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("DELETE запрос на удаление пользователя с id: {}", id);
        userService.delete(id);
    }

    /**
     * Получает список всех пользователей.
     * @return Список DTO всех пользователей.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("GET запрос на получение списка всех пользователей.");
        return userService.findAll();
    }
}
