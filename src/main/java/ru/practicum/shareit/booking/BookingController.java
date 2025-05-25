package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {


    @PostMapping
    public BookingDto create(@RequestBody BookingDto bookingDto) {

    }

    @PatchMapping("/{bookingId}")
    public BookingDto update(@PathVariable Long bookingId, @RequestParam boolean approved) {
        return new Booking();
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable Long bookingId) {
        return new Booking();
    }

    @GetMapping
    public List<BookingDto> findAll(@RequestParam(defaultValue = "ALL") String state) {

        return List.of();
    }

    @GetMapping("/owner")
    public List<BookingDto> getOwnerBookings(@RequestParam(defaultValue = "ALL") String state) {

        return List.of();
    }
}