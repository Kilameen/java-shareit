package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.utils.Marker;

/**
 * DTO (Data Transfer Object) для представления данных пользователя при создании или обновлении.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    /**
     * Имя пользователя.
     * Не должно быть пустым или содержать только пробелы при создании.
     */
    @NotBlank(message = "Имя пользователя не может быть пустым или сожержать только пробелы",
            groups = {Marker.OnCreate.class})
    String name;
    /**
     * Email пользователя.
     * Должен соответствовать формату email и не быть пустым при создании.
     * Должен соответствовать формату email при обновлении.
     */
    @Email(message = "Невалидный email", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @NotBlank(message = "Поле email не должно быть пустым или сожержать только пробелы!",
            groups = {Marker.OnCreate.class})
    String email;
}
