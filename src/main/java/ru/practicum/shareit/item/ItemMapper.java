package ru.practicum.shareit.item;

import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public class ItemMapper {

    public static Item toItem(ItemDto itemDto) {
        return Item.builder()
                .name(itemDto.getName()) // Название предмета
                .description(itemDto.getDescription()) // Описание предмета
                .available(itemDto.getAvailable()) // Доступность предмета
                .build();
    }

    public static NewItemDto toNewItemDto(Item item, NewBookingDto lastBooking, List<NewCommentDto> comments, NewBookingDto nextBooking) {
        return NewItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(lastBooking)
                .comments(comments)
                .nextBooking(nextBooking)
                .build();
    }

    public static NewItemDto toNewItemDto(Item item) {
        return NewItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }
}