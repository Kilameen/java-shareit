package ru.practicum.shareit.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO (Data Transfer Object) для представления запроса вещи.
 * Используется для передачи данных о запросе между слоями приложения.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    /**
     * Уникальный идентификатор запроса.
     */
    Long id;

    /**
     * Описание требуемой вещи. Текст, поясняющий, что именно запрашивает пользователь.
     */
    String description;

    /**
     * Дата и время создания запроса.
     */
    LocalDateTime created;

    /**
     * Список вещей ({@link ItemDto}), отвечающих данному запросу.
     * Может быть пустым, если ни одна вещь еще не была связана с запросом.
     */
    List<ItemDto> items;
}
