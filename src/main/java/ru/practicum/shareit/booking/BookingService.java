package ru.practicum.shareit.booking;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import java.util.List;

public interface BookingService {


    BookingDto create(Long userId, BookingDto bookingDto);

    @Transactional
    BookingDto update(Long bookingId, Boolean approved, Long userId);

    BookingDto getBookingById(Long bookingId, Long userId);

    List<BookingDto> findAll(Long bookerId, String state);

    List<BookingDto> getOwnerBookings(Long ownerId, String state);
}
