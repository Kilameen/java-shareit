package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO для создания новой вещи.
 * Используется для передачи данных о вещи от клиента к серверу
 * при создании новой вещи.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemCreateDto {

    /**
     * Название вещи.
     * Обязательное поле.
     */
    String name;

    /**
     * Описание вещи.
     */
    String description;

    /**
     * Доступность вещи для аренды.
     *  `true`, если вещь доступна, и `false`, если нет.
     *  Обязательное поле.
     */
    Boolean available;

    /**
     * Идентификатор запроса, по которому была создана вещь.
     * Может быть `null`, если вещь не связана с запросом.
     */
    Long requestId;
}
