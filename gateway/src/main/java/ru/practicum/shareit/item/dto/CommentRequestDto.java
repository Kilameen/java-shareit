package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO (Data Transfer Object) для запроса на добавление комментария.
 * Используется для передачи данных от клиента к серверу при создании нового комментария.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequestDto {

    /**
     * Текст комментария.
     * Не может быть пустым или содержать только пробелы.
     * Аннотация `@NotBlank` обеспечивает проверку этого условия.
     */
    @NotBlank(message = "Сообщение не может быть пустым или содержать только пробелы!")
    String text;
}
