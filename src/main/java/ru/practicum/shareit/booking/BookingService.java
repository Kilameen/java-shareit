package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;

import java.util.List;

public interface BookingService {


    NewBookingDto create(Long userId, BookingDto bookingDto);

    NewBookingDto update(Long userId, Long bookingId, Boolean approved);

    NewBookingDto getBookingById(Long userId, Long bookingId);

    List<NewBookingDto> findAll(Long bookerId, String state);

    List<NewBookingDto> getOwnerBookings(Long ownerId, String state);
}
