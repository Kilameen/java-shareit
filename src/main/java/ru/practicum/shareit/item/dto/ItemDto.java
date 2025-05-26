package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.utils.Marker;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemDto {

    Long id;
    @NotBlank(message = "Название товара не может быть пустым или содержать только пробелы", groups = {Marker.OnCreate.class})
    String name;
    @NotBlank(message = "Описание товара не может быть пустым или содержать только пробелы", groups = {Marker.OnCreate.class})
    String description;
    @NotNull(message = "Статус о том, доступна или нет вещь для аренды обязателен", groups = {Marker.OnCreate.class})
    Boolean available;

    List<CommentDto> comments;
    BookingDto lastBooking;
    BookingDto nextBooking;
}
