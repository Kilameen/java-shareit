package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

/**
 * Репозиторий для работы с запросами вещей (ItemRequest) в базе данных.
 *
 * Этот интерфейс расширяет JpaRepository, предоставляя стандартные методы CRUD
 * для сущности ItemRequest, такие как сохранение, удаление, поиск по ID и т.д.
 */
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    /**
     * Находит все запросы вещей, созданные пользователем с указанным ID.
     *
     * JPA автоматически генерирует SQL-запрос на основе названия метода.  В данном случае
     * ищет все записи в таблице ItemRequest, где поле requester_id (связанное с ID пользователя)
     * соответствует переданному userId.
     *
     * @param userId ID пользователя, чьи запросы необходимо найти.
     * @return Список запросов вещей, созданных указанным пользователем.  Возвращает пустой список,
     * если запросы не найдены.
     */
    List<ItemRequest> findAllByRequesterId(Long userId);

}
