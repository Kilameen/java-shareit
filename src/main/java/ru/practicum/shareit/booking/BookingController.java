package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {


    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return booking;
    }

    @PatchMapping("/{bookingId}")
    public Booking update(@PathVariable Long bookingId, @RequestParam boolean approved) {
        return new Booking();
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable Long bookingId) {
        return new Booking();
    }

    @GetMapping
    public List<Booking> findAll(@RequestParam(defaultValue = "ALL") String state) {

        return List.of();
    }

    @GetMapping("/owner")
    public List<Booking> getOwnerBookings(@RequestParam(defaultValue = "ALL") String state) {

        return List.of();
    }
}