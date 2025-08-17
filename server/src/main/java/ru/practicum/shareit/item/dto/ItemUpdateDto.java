package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO для обновления информации о вещи.
 * Используется для передачи данных, подлежащих изменению, от клиента к серверу.
 * Поля, которые не нужно обновлять, могут быть null.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemUpdateDto {

    /**
     * Новое название вещи.
     * Если не требуется обновление, может быть null.
     */
    String name;

    /**
     * Новое описание вещи.
     * Если не требуется обновление, может быть null.
     */
    String description;

    /**
     * Новое значение доступности вещи для аренды.
     * Если не требуется обновление, может быть null.
     * `true` - вещь доступна, `false` - вещь недоступна.
     */
    Boolean available;

    /**
     * Новый идентификатор запроса, по которому была создана вещь.
     * Если не требуется обновление или вещь не связана с запросом, может быть null.
     */
    Long requestId;
}
