package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.user.dto.UserRequestDto;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookItemRequestDto {
   @NotNull
    Long itemId;
    @NotNull
    @FutureOrPresent
     LocalDateTime start;
     @NotNull
    @Future
     LocalDateTime end;
}