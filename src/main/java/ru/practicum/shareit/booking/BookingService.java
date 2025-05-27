package ru.practicum.shareit.booking;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import java.util.List;

public interface BookingService {


    BookingDto create(Long userId, BookingDto bookingDto);

    @Transactional
    BookingDto update(Long userId, Long bookingId, Boolean approved);

    BookingDto getBookingById(Long userId, Long bookingId);

    List<BookingDto> findAll(Long bookerId, String state);

    List<BookingDto> getOwnerBookings(Long ownerId, String state);
}
