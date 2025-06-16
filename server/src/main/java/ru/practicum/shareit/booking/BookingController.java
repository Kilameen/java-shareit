package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.utils.Constants;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto create(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                             @RequestBody BookingCreateDto bookingCreateDto) {
        log.info("POST запрос на создание нового бронирования вещи от пользователя c id: {} ", userId);
        return bookingService.create(userId, bookingCreateDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto update(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                             @PathVariable Long bookingId,
                             @RequestParam Boolean approved) {
        log.info("PATCH запрос на обновление бронирования вещи от пользователя c id: {} ", userId);
        return bookingService.update(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto findBookingById(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                      @PathVariable("bookingId") Long bookingId) {
        log.info("GET запрос на получение данных о  бронировании от пользователя с id: {}", userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> findAll(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                    @RequestParam(value = "state", defaultValue = "ALL") String bookingState,
                                    @RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        if (from < 0 || size <= 0) {
            throw new IllegalArgumentException("Параметры 'from' и 'size' должны быть положительными числами, 'size' > 0.");
        }
        log.info("GET запрос на получение списка всех бронирований текущего пользователя с id: {} и статусом {}", userId, bookingState);
        return bookingService.findAll(userId, bookingState, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllOwner(@RequestHeader(Constants.USER_ID_HEADER) Long ownerId,
                                        @RequestParam(value = "state", defaultValue = "ALL") String bookingState,
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @RequestParam(defaultValue = "10") Integer size) {
        if (from < 0 || size <= 0) {
            throw new IllegalArgumentException("Параметры 'from' и 'size' должны быть положительными числами, 'size' > 0.");
        }
        log.info("GET запрос на получение списка всех бронирований текущего владельца с id: {} и статусом {}", ownerId, bookingState);
        return bookingService.getOwnerBookings(ownerId, bookingState, from, size);
    }
}