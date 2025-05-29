package ru.practicum.shareit.item;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public class ItemMapper {

    // Преобразует ItemDto в Item. Используется при создании нового предмета или обновлении существующего.
    public static Item toItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId()) // ID предмета
                .name(itemDto.getName()) // Название предмета
                .description(itemDto.getDescription()) // Описание предмета
                .available(itemDto.getAvailable()) // Доступность предмета
                .build();
    }


    public static ItemDto toItemDto(Item item, BookingDto lastBooking, List<CommentDto> comments, BookingDto nextBooking) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(lastBooking)
                .comments(comments)
                .nextBooking(nextBooking)
                .build();
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }
}
