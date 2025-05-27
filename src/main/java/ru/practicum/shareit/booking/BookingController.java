package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.utils.Marker;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor // Добавлена аннотация для генерации конструктора с внедрением зависимостей
@Slf4j // Добавлена аннотация для логирования
public class BookingController {

    private final BookingService bookingService;
    @Validated(Marker.OnCreate.class)
    @PostMapping
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @Valid @RequestBody BookingDto bookingDto) {
        log.info("POST запрос на создание нового бронирования вещи от пользователя c id: {} ", userId);
        return bookingService.create(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                   @PathVariable("bookingId") Long bookingId,
                                   @RequestParam(name = "approved") Boolean approved) {
        return bookingService.update(userId, bookingId, approved); // Исправлено: передаем bookingId и userId
    }


    @GetMapping("/{bookingId}")
    public BookingDto findBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @PathVariable("bookingId") Long bookingId) {
        log.info("GET запрос на получение данных о  бронировании от пользователя с id: {}", userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @RequestParam(value = "state", defaultValue = "ALL") String bookingState) {
        log.info("GET запрос на получение списка всех бронирований текущего пользователя с id: {} и статусом {}", userId, bookingState);
        return bookingService.findAll(userId, bookingState);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                        @RequestParam(value = "state", defaultValue = "ALL") String bookingState) {
        log.info("GET запрос на получение списка всех бронирований текущего владельца с id: {} и статусом {}", ownerId, bookingState);
        return bookingService.getOwnerBookings(ownerId, bookingState);
    }
}
