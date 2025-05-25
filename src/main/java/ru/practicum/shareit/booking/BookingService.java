package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import java.util.List;

public interface BookingService {


    BookingDto create(Long userId, BookingDto bookingDto);

    BookingDto update(Long bookingId, Boolean approved);

    BookingDto getBookingById(Long bookingId);

    List<BookingDto> findAll(Long bookerId, String state);

    List<BookingDto> getOwnerBookings(Long ownerId, String state);
}
