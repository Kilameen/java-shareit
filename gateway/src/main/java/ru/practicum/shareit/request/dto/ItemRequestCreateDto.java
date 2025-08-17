package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO (Data Transfer Object) для создания запроса вещи.
 * Используется для передачи данных о запросе от клиента к серверу.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestCreateDto {

    /**
     * Описание запроса вещи.
     * Обязательное поле, не должно быть пустым или содержать только пробелы.
     */
    @NotBlank(message = "Описание запроса не должно быть пустым или содержать только пробелы!")
    String description;
}
