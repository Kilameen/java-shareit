package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO (Data Transfer Object) для запроса на бронирование вещи.
 * Используется для передачи данных от клиента к серверу при создании нового бронирования.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookItemRequestDto {
    /**
     * Идентификатор вещи, которую необходимо забронировать.
     * Поле обязательно для заполнения ({@code @NotNull}).
     */
    @NotNull(message = "itemId не может быть null")
    Long itemId;

    /**
     * Дата и время начала бронирования.
     * Поле обязательно для заполнения ({@code @NotNull}) и должно быть в будущем или настоящем ({@code @FutureOrPresent}).
     */
    @NotNull(message = "Время начала бронирования не должно быть null")
    @FutureOrPresent(message = "Время начала бронирования должно быть в будущем или настоящем! ")
    LocalDateTime start;

    /**
     * Дата и время окончания бронирования.
     * Поле обязательно для заполнения ({@code @NotNull}) и должно быть в будущем ({@code @Future}).
     */
    @NotNull(message = "Время окончания бронирования не должно быть null")
    @Future(message = "Время окончания бронирования должно быть в будущем!")
    LocalDateTime end;
}
