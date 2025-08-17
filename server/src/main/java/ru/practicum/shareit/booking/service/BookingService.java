package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    /**
     * Создает новое бронирование.
     *
     * @param userId           ID пользователя, создающего бронирование.
     * @param bookingCreateDto DTO с данными для создания бронирования.
     * @return DTO созданного бронирования.
     */
    BookingDto create(Long userId, BookingCreateDto bookingCreateDto);

    /**
     * Обновляет статус бронирования (подтверждает или отклоняет).
     *
     * @param userId    ID пользователя, выполняющего обновление (владельца вещи).
     * @param bookingId ID бронирования, которое нужно обновить.
     * @param approved  `true`, если бронирование подтверждено, `false` - отклонено.
     * @return DTO обновленного бронирования.
     */
    BookingDto update(Long userId, Long bookingId, Boolean approved);

    /**
     * Возвращает бронирование по его ID.
     *
     * @param userId    ID пользователя, запрашивающего бронирование.
     * @param bookingId ID бронирования, которое нужно вернуть.
     * @return DTO бронирования.
     */
    BookingDto getBookingById(Long userId, Long bookingId);

    /**
     * Возвращает список всех бронирований для указанного пользователя (арендатора).
     *
     * @param bookerId ID пользователя-арендатора.
     * @param state    Строковое представление состояния бронирования (ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED).
     * @param from     Индекс первого элемента, начиная с которого возвращается список.
     * @param size     Количество элементов для возврата.
     * @return Список DTO бронирований.
     */
    List<BookingDto> findAll(Long bookerId, String state, Integer from, Integer size);

    /**
     * Возвращает список бронирований для вещей, принадлежащих указанному пользователю (владельцу).
     *
     * @param ownerId ID пользователя-владельца вещей.
     * @param state   Строковое представление состояния бронирования.
     * @param from    Индекс первого элемента.
     * @param size    Количество элементов для возврата.
     * @return Список DTO бронирований.
     */
    List<BookingDto> getOwnerBookings(Long ownerId, String state, Integer from, Integer size);
}
