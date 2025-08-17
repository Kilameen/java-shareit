package ru.practicum.shareit.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * * DTO (Data Transfer Object) для создания запроса вещи.
 * Используется для передачи данных от клиента к серверу при создании нового запроса.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestCreateDto {
    /**
     * Описание запрашиваемой вещи.
     * Содержит текст, описывающий, что пользователь хочет получить.
     */
    String description;

    /**
     * Дата и время создания запроса.
     * Сервер может установить это значение автоматически при создании запроса.
     */
    LocalDateTime created;
}
