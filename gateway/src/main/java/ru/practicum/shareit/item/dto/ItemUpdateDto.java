package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemUpdateDto {
    String name;
    String description;
    Boolean available;
    Long requestId;
}