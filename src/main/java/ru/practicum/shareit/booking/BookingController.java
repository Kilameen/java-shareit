package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public NewBookingDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                @Valid @RequestBody BookingDto bookingDto) {
        log.info("POST запрос на создание нового бронирования вещи от пользователя c id: {} ", userId);
        return bookingService.create(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public NewBookingDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                @PathVariable("bookingId") Long bookingId,
                                @RequestParam(name = "approved") Boolean approved) {
        return bookingService.update(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public NewBookingDto findBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable("bookingId") Long bookingId) {
        log.info("GET запрос на получение данных о  бронировании от пользователя с id: {}", userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public List<NewBookingDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @RequestParam(value = "state", defaultValue = "ALL") String bookingState) {
        log.info("GET запрос на получение списка всех бронирований текущего пользователя с id: {} и статусом {}", userId, bookingState);
        return bookingService.findAll(userId, bookingState);
    }

    @GetMapping("/owner")
    public List<NewBookingDto> getAllOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                           @RequestParam(value = "state", defaultValue = "ALL") String bookingState) {
        log.info("GET запрос на получение списка всех бронирований текущего владельца с id: {} и статусом {}", ownerId, bookingState);
        return bookingService.getOwnerBookings(ownerId, bookingState);
    }
}