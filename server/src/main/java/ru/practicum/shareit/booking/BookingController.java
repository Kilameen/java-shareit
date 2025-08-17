package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.utils.Constants;

import java.util.List;
/**
 * Контроллер для обработки запросов, связанных с бронированием (Booking).
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    /**
     * Обрабатывает POST-запрос на создание нового бронирования.
     * @param userId ID пользователя, выполняющего запрос, берется из заголовка.
     * @param bookingCreateDto DTO с данными для создания бронирования.
     * @return DTO созданного бронирования.
     */
    @PostMapping
    public BookingDto create(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                             @RequestBody BookingCreateDto bookingCreateDto) {
        log.info("POST запрос на создание нового бронирования вещи от пользователя c id: {} ", userId);
        return bookingService.create(userId, bookingCreateDto);
    }

    /**
     * Обрабатывает PATCH-запрос на обновление статуса бронирования (подтверждение/отклонение).
     * @param userId ID пользователя, выполняющего запрос (владельца вещи), берется из заголовка.
     * @param bookingId ID бронирования, которое нужно обновить, берется из пути.
     * @param approved Параметр, указывающий, подтверждено ли бронирование (true) или отклонено (false).
     * @return DTO обновленного бронирования.
     */
    @PatchMapping("/{bookingId}")
    public BookingDto update(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                             @PathVariable Long bookingId,
                             @RequestParam Boolean approved) {
        log.info("PATCH запрос на обновление бронирования вещи от пользователя c id: {} ", userId);
        return bookingService.update(userId, bookingId, approved);
    }

    /**
     * Обрабатывает GET-запрос на получение информации о бронировании по ID.
     * @param userId ID пользователя, выполняющего запрос, берется из заголовка.
     * @param bookingId ID бронирования, которое нужно получить, берется из пути.
     * @return DTO бронирования.
     */
    @GetMapping("/{bookingId}")
    public BookingDto findBookingById(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                      @PathVariable("bookingId") Long bookingId) {
        log.info("GET запрос на получение данных о  бронировании от пользователя с id: {}", userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    /**
     * Обрабатывает GET-запрос на получение списка бронирований пользователя.
     * @param userId ID пользователя, выполняющего запрос, берется из заголовка.
     * @param bookingState Фильтр по состоянию бронирования (ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED).
     * @param from Начальная позиция для постраничного вывода.
     * @param size Количество бронирований на странице.
     * @return Список DTO бронирований.
     */
    @GetMapping
    public List<BookingDto> findAll(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                    @RequestParam(value = "state", defaultValue = "ALL") String bookingState,
                                    @RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET запрос на получение списка всех бронирований текущего пользователя с id: {} и статусом {}", userId, bookingState);
        return bookingService.findAll(userId, bookingState, from, size);
    }

    /**
     * Обрабатывает GET-запрос на получение списка бронирований вещей, принадлежащих пользователю (владельцу).
     * @param ownerId ID владельца вещей, берется из заголовка.
     * @param bookingState Фильтр по состоянию бронирования.
     * @param from Начальная позиция для постраничного вывода.
     * @param size Количество бронирований на странице.
     * @return Список DTO бронирований.
     */
    @GetMapping("/owner")
    public List<BookingDto> getAllOwner(@RequestHeader(Constants.USER_ID_HEADER) Long ownerId,
                                        @RequestParam(value = "state", defaultValue = "ALL") String bookingState,
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET запрос на получение списка всех бронирований текущего владельца с id: {} и статусом {}", ownerId, bookingState);
        return bookingService.getOwnerBookings(ownerId, bookingState, from, size);
    }
}
