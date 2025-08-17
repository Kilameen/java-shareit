package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для создания бронирования.
 * Используется для передачи данных о бронировании от клиента к серверу.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingCreateDto {

    /**
     * Дата и время начала бронирования.
     * Не может быть null.  Должна быть раньше, чем `end`.
     */
    LocalDateTime start;

    /**
     * Дата и время окончания бронирования.
     * Не может быть null. Должна быть позже, чем `start`.
     */
    LocalDateTime end;

    /**
     * Идентификатор вещи, которую пользователь хочет забронировать.
     * Должен быть существующим идентификатором вещи в системе.
     */
    Long itemId;

    /**
     * Идентификатор пользователя, который создает бронирование.
     * Должен быть существующим идентификатором пользователя в системе.
     */
    Long bookerId;
    /**
     * Статус бронирования.
     */
    Status status;
}
