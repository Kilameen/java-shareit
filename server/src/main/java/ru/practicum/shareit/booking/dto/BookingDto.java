package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object), представляющий информацию о бронировании.
 * Используется для передачи данных о бронировании между слоями приложения.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    /**
     * Уникальный идентификатор бронирования.
     */
    Long id;

    /**
     * Дата и время начала бронирования.
     */
    LocalDateTime start;

    /**
     * Дата и время окончания бронирования.
     */
    LocalDateTime end;

    /**
     * DTO, представляющий информацию о забронированной вещи.
     */
    ItemDto item;

    /**
     * DTO, представляющий информацию о пользователе, забронировавшем вещь.
     */
    UserDto booker;

    /**
     * Статус бронирования (WAITING, APPROVED, REJECTED, CANCELED).
     */
    Status status;
}
