package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemDto {
    @NotNull(message = "Id товара должно быть указано")
    Long id;
    @NotBlank(message = "Название товара не может быть пустым иил содержать только пробелы")
    String name;
    @NotBlank(message = "Описание товара не может быть пустым иил содержать только пробелы")
    String description;
    @NotNull(message = "Статус о том, доступна или нет вещь для аренды обязателен")
    boolean available;
    Long request;
}
