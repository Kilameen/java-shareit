package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.exception.ValidationException;

/**
 * Enum `State` представляет различные состояния бронирования,
 * используемые для фильтрации списка бронирований при запросе.
 */
public enum State {
    /**
     * Возвращает все бронирования, независимо от их состояния.
     */
    ALL,
    /**
     * Возвращает бронирования, которые активны в настоящий момент времени
     * (дата начала уже наступила, а дата окончания ещё не наступила).
     */
    CURRENT,
    /**
     * Возвращает бронирования, которые запланированы на будущее
     * (дата начала ещё не наступила).
     */
    FUTURE,
    /**
     * Возвращает бронирования, которые уже завершились
     * (дата окончания уже наступила).
     */
    PAST,
    /**
     * Возвращает бронирования, которые были отклонены.
     */
    REJECTED,
    /**
     * Возвращает бронирования, ожидающие подтверждения.
     */
    WAITING;

    /**
     * Метод `parseState` преобразует строковое представление состояния в enum `State`.
     *
     * @param stateString строковое представление состояния ("ALL", "CURRENT").
     * @return соответствующий enum `State`.
     * @throws ValidationException если переданная строка не соответствует ни одному из допустимых состояний.
     */
    public static State parseState(String stateString) {
        try {
            return State.valueOf(stateString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Неизвестный state: " + stateString);
        }
    }
}
