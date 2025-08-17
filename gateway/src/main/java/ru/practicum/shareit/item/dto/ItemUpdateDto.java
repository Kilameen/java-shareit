package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для обновления информации о предмете (Item).
 * Поля могут быть null, что означает, что их не нужно обновлять.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemUpdateDto {

    /**
     * Новое название предмета.
     * Если null, название не изменяется.
     */
    String name;

    /**
     * Новое описание предмета.
     * Если null, описание не изменяется.
     */
    String description;

    /**
     * Новый статус доступности предмета.
     * Если null, статус не изменяется.
     */
    Boolean available;

    /**
     * ID запроса, связанного с предметом.
     * Если null, связь не изменяется.  Обычно не используется при обновлении.
     */
    Long requestId;
}
