package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     *  Этот метод извлекает все комментарии, связанные с определенным ID предмета (itemId).
     *  Используется для получения списка всех отзывов или заметок, оставленных другими пользователями
     *  относительно конкретного предмета, например, товара или услуги.
     *
     * @param itemId ID предмета, для которого необходимо получить комментарии. Не может быть null.
     * @return Список объектов `Comment`, представляющих комментарии к предмету.
     *         Возвращает пустой список, если комментарии для данного itemId отсутствуют.
     */
    List<Comment> findAllByItemId(Long itemId);
}
