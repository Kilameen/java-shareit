package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class UserDto {
    @NotNull(message = "Id Пользователя не может быть пустым")
    Long id;
    @NotBlank(message = "Имя пользователя не может быть пустым")
    String name;
    @Email
    @NotBlank(message = "Поле email должно быть заполнено")
    String email;
}

