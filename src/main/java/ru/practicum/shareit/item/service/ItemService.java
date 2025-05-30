package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.NewItemDto;

import java.util.Collection;
import java.util.List;

public interface ItemService {

    NewItemDto create(Long userId, ItemDto itemDto);

    NewItemDto update(Long userId, Long itemId, ItemDto itemDto);

    NewItemDto getItemDtoById(Long userId, Long itemId);

    Collection<NewItemDto> getAllItemDtoByUserId(Long userId);

    List<NewItemDto> searchItems(Long userId, String text);

    void deleteItem(Long itemId);

    NewCommentDto createComment(Long userId, CommentDto commentDto, Long itemId);

    List<NewCommentDto> getItemComments(Long itemId);
}
