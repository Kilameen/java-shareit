package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Booking {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    Item item;
    User booker;
    Status status;
}
