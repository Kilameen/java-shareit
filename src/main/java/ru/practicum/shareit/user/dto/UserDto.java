package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class UserDto {

    Long id;
    @NotBlank(message = "Имя пользователя не может быть пустым")
    String name;
    @Email (message = "Невалидный email")
    @NotBlank(message = "Поле email должно быть заполнено")
    String email;
}
