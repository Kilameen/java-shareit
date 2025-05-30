package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.NewBookingDto;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class NewItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private NewBookingDto lastBooking;
    private List<NewCommentDto> comments;
    private NewBookingDto nextBooking;
}
