package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Item {
    Long id;
    String name;
    String description;
    Boolean available;
    Long owner;
    Long request;
}
