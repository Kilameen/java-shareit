package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingMapperTest {

    @Test
    void toBookingDtoMapBookingToBookingDto() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);

        User booker = User.builder()
                .id(1L)
                .name("Test Booker")
                .email("test@yandex.ru")
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .owner(booker)
                .build();

        Booking booking = Booking.builder()
                .id(1L)
                .start(start)
                .end(end)
                .item(item)
                .booker(booker)
                .status(Status.WAITING)
                .build();

        BookingDto bookingDto = BookingMapper.toBookingDto(booking);

        assertEquals(booking.getId(), bookingDto.getId());
        assertEquals(booking.getStart(), bookingDto.getStart());
        assertEquals(booking.getEnd(), bookingDto.getEnd());
        assertEquals(booking.getStatus(), bookingDto.getStatus());
        assertEquals(booking.getItem().getId(), bookingDto.getItem().getId());
        assertEquals(booking.getBooker().getId(), bookingDto.getBooker().getId());

    }

    @Test
    void toBookingFromCreateDtoMapBookingCreateDtoToBooking() {

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);

        BookingCreateDto bookingCreateDto = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .status(Status.WAITING)
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .build();

        User booker = User.builder()
                .id(1L)
                .name("Test Booker")
                .email("test@yandex.ru")
                .build();

        Booking booking = BookingMapper.toBookingFromCreateDto(bookingCreateDto, item, booker);

        assertEquals(bookingCreateDto.getStart(), booking.getStart());
        assertEquals(bookingCreateDto.getEnd(), booking.getEnd());
        assertEquals(bookingCreateDto.getStatus(), booking.getStatus());
        assertEquals(item, booking.getItem());
        assertEquals(booker, booking.getBooker());
    }
}