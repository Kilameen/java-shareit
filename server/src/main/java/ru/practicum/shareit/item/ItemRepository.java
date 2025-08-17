package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    /**
     * Находит все вещи, принадлежащие определенному пользователю.
     * @param owner Пользователь, которому принадлежат вещи.
     * @return Список вещей, принадлежащих пользователю.
     */
    List<Item> findByOwner(User owner);

    /**
     * Выполняет поиск вещей по тексту в имени или описании.
     * @param text Текст для поиска (нечувствителен к регистру).
     * @return Список доступных вещей, в имени или описании которых содержится текст.
     */
    @Query(value = "SELECT i " +
            "FROM Item as i " +
            "WHERE i.available = true and " +
            "(LOWER(i.name) LIKE LOWER(CONCAT('%', ?1, '%') ) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', ?1, '%') ))")
    List<Item> searchItems(String text);

    /**
     * Находит вещи, связанные с определенным запросом.
     * @param requestId Идентификатор запроса.
     * @return Список вещей, связанных с запросом.
     */
    List<Item> findByRequestId(Long requestId);
}
