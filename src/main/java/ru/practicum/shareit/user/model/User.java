package ru.practicum.shareit.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    Long id;
    String name;
    String email;
}
