package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewBookingDto {
    Long id;
    NewItemDto item;
    LocalDateTime start;
    LocalDateTime end;
    UserDto booker;
    Status status;
}