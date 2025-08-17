package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO (Data Transfer Object) для создания комментария.
 * Используется для передачи данных о комментарии от клиента к серверу.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreateDto {
    /**
     * Текст комментария.
     * Не может быть null или пустым.  Описывает суть комментария.
     */
    String text;
}
