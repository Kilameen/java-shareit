package ru.practicum.shareit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User user;
    private Item item;
    private ItemCreateDto itemCreateDto;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("Test").email("Test@yandex.ru").build();
        itemCreateDto = ItemCreateDto.builder().name("TestItem").description("For test").available(true).build();
        item = Item.builder().id(1L).name("TestItem").description("For test").available(true).owner(user).build();
    }

    @Test
    void createItemReturnItemDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto itemDto = itemService.create(1L, itemCreateDto);

        assertEquals(itemCreateDto.getName(), itemDto.getName());
        assertEquals(itemCreateDto.getDescription(), itemDto.getDescription());
        assertEquals(itemCreateDto.getAvailable(), itemDto.getAvailable());
    }

    @Test
    void createItemWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            itemService.create(1L, itemCreateDto);
        });

        assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
    }
}