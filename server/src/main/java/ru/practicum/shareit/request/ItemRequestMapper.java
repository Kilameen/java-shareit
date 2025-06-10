package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemRequestMapper {

    public static ItemRequest toItemRequestFromCreateDto(User user,ItemRequestCreateDto itemRequestCreateDto) {
        return ItemRequest.builder()
                .description(itemRequestCreateDto.getDescription())
                .build();
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        List<ItemDto> itemsDtoOut = new ArrayList<>();

        if (itemRequest.getItems() != null) { // Проверка на null
            itemsDtoOut = itemRequest.getItems().stream()
                    .map(ItemMapper::toItemFromDto)
                    .collect(Collectors.toList());
        }
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(itemsDtoOut)
                .build();
    }

}
