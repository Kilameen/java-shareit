package ru.practicum.shareit.booking.dto;

import java.util.Optional;

/**
 * Enum `BookingState` представляет состояния бронирования.
 * Используется для фильтрации бронирований при запросах.
 */
public enum BookingState {
    /**
     * Все бронирования, независимо от их состояния.
     */
    ALL,
    /**
     * Текущие бронирования: бронирования, которые начались и еще не закончились.
     */
    CURRENT,
    /**
     * Будущие бронирования: бронирования, которые еще не начались.
     */
    FUTURE,
    /**
     * Завершенные бронирования: бронирования, которые уже закончились.
     */
    PAST,
    /**
     * Отклоненные бронирования: бронирования, которые были отклонены владельцем вещи.
     */
    REJECTED,
    /**
     * Бронирования, ожидающие подтверждения владельцем вещи.
     */
    WAITING;

    /**
     * Метод `from` преобразует строковое представление состояния бронирования в enum `BookingState`.
     *
     * @param stringState Строковое представление состояния бронирования (например, "WAITING", "CURRENT").
     * @return Optional, содержащий `BookingState`, если строковое представление соответствует одному из значений enum.
     *         Возвращает пустой Optional, если строковое представление не соответствует ни одному из значений enum.
     *         Метод игнорирует регистр символов в строковом представлении.
     */
    public static Optional<BookingState> from(String stringState) {
        for (BookingState state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
