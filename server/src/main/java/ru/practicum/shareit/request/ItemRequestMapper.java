package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

public class ItemRequestMapper {

    public static ItemRequest toItemRequestFromCreateDto(ItemRequestCreateDto itemRequestCreateDto) {

        return ItemRequest.builder()
                .description(itemRequestCreateDto.getDescription())
                .build();
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {

        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requester(itemRequest.getRequester())
                .created(itemRequest.getCreated())
                .items(itemRequest)
                .build();
    }
}