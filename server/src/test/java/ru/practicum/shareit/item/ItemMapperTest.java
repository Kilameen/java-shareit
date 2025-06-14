package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    @Test
    void toItemFromCreateDto() {
        ItemCreateDto itemCreateDto = ItemCreateDto.builder()
                .name("test")
                .description("description")
                .available(true)
                .build();

        Item item = ItemMapper.toItemFromCreateDto(itemCreateDto);

        assertEquals(itemCreateDto.getName(), item.getName());
        assertEquals(itemCreateDto.getDescription(), item.getDescription());
        assertEquals(itemCreateDto.getAvailable(), item.getAvailable());
    }

    @Test
    void toItemFromDto() {
        User owner = User.builder().id(1L).name("Owner").email("test@yandex.ru").build();
        Item item = Item.builder()
                .id(1L)
                .name("test")
                .description("test description")
                .available(true)
                .owner(owner)
                .build();

        ItemDto itemDto = ItemMapper.toItemFromDto(item);

        assertEquals(item.getId(), itemDto.getId());
        assertEquals(item.getName(), itemDto.getName());
        assertEquals(item.getDescription(), itemDto.getDescription());
        assertEquals(item.getAvailable(), itemDto.getAvailable());
        assertEquals(UserMapper.toUserDto(item.getOwner()), itemDto.getOwner());
        assertNull(itemDto.getRequestId());
    }
}