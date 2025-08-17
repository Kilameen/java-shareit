package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления комментария.
 * Используется для передачи информации о комментарии между слоями приложения.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    /**
     * Уникальный идентификатор комментария.
     */
    Long id;

    /**
     * Идентификатор вещи, к которой относится комментарий.
     */
    Long itemId;

    /**
     * Текст комментария.
     */
    String text;

    /**
     * Имя автора комментария.
     */
    String authorName;

    /**
     * Дата и время создания комментария.
     */
    LocalDateTime created;
}
