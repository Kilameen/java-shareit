package ru.practicum.shareit.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;
import java.util.List;

public interface ItemService {

    ItemDto create(Long userId, ItemDto itemDto);

    ItemDto update(Long userId, Long itemId, ItemDto itemDto);

    ItemDto getItemDtoById(Long userId, Long itemId);

    Collection<ItemDto> getAllItemDtoByUserId(Long userId);

    List<ItemDto> searchItems(String text);

    void deleteItem(Long itemId);

    CommentDto createComment(Long userId, CommentDto commentDto, Long itemId);
}
