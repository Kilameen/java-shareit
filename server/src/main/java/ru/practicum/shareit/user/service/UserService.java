package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

/**
 * Интерфейс сервиса для управления пользователями.
 * Определяет основные операции, которые можно выполнять над сущностью User.
 */
public interface UserService {

    /**
     * Создает нового пользователя.
     * @param userDto DTO с информацией о пользователе, которого необходимо создать.
     * @return DTO созданного пользователя.
     */
    UserDto create(UserDto userDto);

    /**
     * Обновляет существующего пользователя.
     * @param id Идентификатор пользователя, которого необходимо обновить.
     * @param userUpdateDto DTO с информацией об обновленных данных пользователя.
     * @return DTO обновленного пользователя.
     */
    UserDto update(Long id, UserDto userUpdateDto);

    /**
     * Находит пользователя по его идентификатору.
     * @param id Идентификатор пользователя.
     * @return DTO пользователя.
     */
    UserDto findUserById(Long id);

    /**
     * Удаляет пользователя по его идентификатору.
     * @param id Идентификатор пользователя, которого необходимо удалить.
     */
    void delete(Long id);

    /**
     * Возвращает список всех пользователей.
     * @return Список DTO пользователей.
     */
    List<UserDto> findAll();
}
