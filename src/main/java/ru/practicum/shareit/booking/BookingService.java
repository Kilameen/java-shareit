package ru.practicum.shareit.booking;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    @Transactional
    BookingDto create(Long userId, BookingDto bookingDto);

    Booking update(Long bookingId, Boolean approved);

    Booking getBookingById(Long bookingId);

    List<Booking> findAll(String state);

    List<Booking> getOwnerBookings(String state);
}
