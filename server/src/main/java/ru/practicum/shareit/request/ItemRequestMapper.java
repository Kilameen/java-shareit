package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserMapper;

public class ItemRequestMapper {

    public static ItemRequest toItemRequestFromCreateDto(ItemRequestCreateDto itemRequestCreateDto) {

        return ItemRequest.builder()
                .id(itemRequestCreateDto.getId())
                .description(itemRequestCreateDto.getDescription())
                .created(itemRequestCreateDto.getCreated())
                .build();
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {

        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requester(itemRequest.getRequester())
                .created(itemRequest.getCreated())
                .items(itemRequest.getItems())
                .build();
    }
}