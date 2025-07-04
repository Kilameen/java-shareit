package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    Long id;
    String name;
    String description;
    Boolean available;
    UserDto owner;
    BookingDto lastBooking;
    List<CommentDto> comments;
    BookingDto nextBooking;
    Long requestId;
}