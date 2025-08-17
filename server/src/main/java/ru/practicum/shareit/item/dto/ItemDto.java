package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления информации о вещи.
 * Используется для передачи данных о вещи между слоями приложения.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    /**
     * Уникальный идентификатор вещи.
     */
    Long id;
    /**
     * Название вещи.
     */
    String name;
    /**
     * Описание вещи.
     */
    String description;
    /**
     * Доступность вещи для аренды (true - доступна, false - нет).
     */
    Boolean available;
    /**
     * DTO владельца вещи.
     */
    UserDto owner;
    /**
     * DTO последнего бронирования вещи. Может быть null.
     */
    BookingDto lastBooking;
    /**
     * Список DTO комментариев к вещи.
     */
    List<CommentDto> comments;
    /**
     * DTO следующего бронирования вещи. Может быть null.
     */
    BookingDto nextBooking;
    /**
     * Идентификатор запроса, по которому была создана вещь (если есть).
     * Может быть null.
     */
    Long requestId;
}
