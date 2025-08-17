package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для создания нового предмета (item).
 * Используется для передачи данных о предмете от клиента к серверу при создании.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemCreateDto {

    /**
     * Название предмета.
     * Не может быть пустым или содержать только пробелы.
     */
    @NotBlank(message = "Название товара не может быть пустым или содержать только пробелы")
    String name;

    /**
     * Описание предмета.
     * Не может быть пустым или содержать только пробелы.
     */
    @NotBlank(message = "Описание товара не может быть пустым или содержать только пробелы")
    String description;

    /**
     * Доступность предмета для аренды.
     * Обязательно должно быть указано (true или false).
     */
    @NotNull(message = "Статус о том, доступна или нет вещь для аренды обязателен")
    Boolean available;

    /**
     * ID запроса, на основании которого создается предмет.
     * Может быть null, если предмет создается не на основании запроса.
     */
    Long requestId;
}
