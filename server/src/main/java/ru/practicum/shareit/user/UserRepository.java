package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

/**
 * Репозиторий для работы с сущностью User в базе данных.
 *
 * Этот интерфейс расширяет JpaRepository, предоставляя стандартные методы
 * для выполнения CRUD операций с сущностью User, такие как сохранение,
 * удаление, обновление и поиск.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Проверяет, существует ли пользователь с указанным email в базе данных.
     *
     * Этот метод выполняет запрос к базе данных для проверки наличия пользователя
     * с заданным email. Он используется, например, при создании нового пользователя,
     * чтобы убедиться, что email не занят.
     *
     * @param email Email пользователя, наличие которого нужно проверить.
     * @return true, если пользователь с указанным email существует, иначе false.
     */
    boolean existsByEmail(String email);
}
