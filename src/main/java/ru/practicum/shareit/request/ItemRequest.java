package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemRequest {
    Long id;
    String description;
    Long requestor;
    LocalDateTime created;
}
