package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {

    public static NewBookingDto toNewBookingDto(Booking booking) {
        return NewBookingDto.builder()
                .id(booking.getId())
                .item(ItemMapper.toNewItemDto(booking.getItem()))
                .start(booking.getStart())
                .end(booking.getEnd())
                .booker(UserMapper.toUserDto(booking.getBooker()))
                .status(booking.getStatus())
                .build();

    }

    public static Booking toBooking(BookingDto bookingDto, Item item, User user) {
        return Booking.builder()
                .item(item)
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .booker(user)
                .status(Status.WAITING)
                .build();
    }
}