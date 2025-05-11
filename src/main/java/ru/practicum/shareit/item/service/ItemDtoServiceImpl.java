package ru.practicum.shareit.item.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemDtoServiceImpl implements ItemDtoService {

    private final ItemService itemService;
    private final UserService userService;

    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {
        // Check if the user exists
        if (userService.findUserById(userId) == null) {
            throw new NotFoundException("Пользователь с ID " + userId + " не найден.");
        }
        validateItemDto(itemDto);

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(userId);
        Item createdItem = itemService.create(item);
        log.info("Создан предмет с ID: {}", createdItem.getId());
        return ItemMapper.toItemDto(createdItem);
    }

    @Override
    public ItemDto update(Long itemId, ItemDto itemDto, Long userId) {
        Item itemFromDto = ItemMapper.toItem(itemDto);
        Item updatedItem = itemService.update(itemId, itemFromDto, userId);
        log.info("Обновлен предмет с ID: {}", itemId);
        return ItemMapper.toItemDto(updatedItem);
    }

    @Override
    public ItemDto getItemDtoById(Long itemId) {
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            throw new NotFoundException("Предмет с ID " + itemId + " не найден.");
        }
        log.info("Получен предмет с ID: {}", itemId);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public Collection<ItemDto> getAllItemDtoByUserId(Long userId) {
        return itemService.findAll().stream()
                .filter(item -> Objects.equals(item.getOwner(), userId))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        List<Item> items = itemService.searchItems(text);
        log.info("Поиск предметов по тексту: {}", text);
        return items.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(Long itemId) {
        itemService.deleteItem(itemId);
        log.info("Удален предмет с ID: {}", itemId);
    }

    private void validateItemDto(ItemDto itemDto) {
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("Поле available не может быть null");
        }

        if (itemDto.getName() == null || itemDto.getName().trim().isEmpty()) {
            throw new ValidationException("Имя предмета не может быть пустым.");
        }

        if (itemDto.getDescription() == null || itemDto.getDescription().trim().isEmpty()) {
            throw new ValidationException("Описание предмета не может быть пустым.");
        }
    }
}