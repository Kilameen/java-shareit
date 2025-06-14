package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testItemDtoSerialization() throws IOException {
        // Создаём экземпляр ItemDto для сериализации
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .owner(new UserDto(1L, "Test User", "test@example.com"))
                .lastBooking(new BookingDto())
                .comments(Collections.emptyList())
                .nextBooking(new BookingDto())
                .requestId(1L)
                .build();

        String json = objectMapper.writeValueAsString(itemDto);
        assertNotNull(json);
        ItemDto deserializedItemDto = objectMapper.readValue(json, ItemDto.class);
        assertEquals(itemDto, deserializedItemDto);
    }

    @Test
    void testItemDtoDeserialization() throws IOException {
        String json = "{\"id\":1,\"name\":\"Test Item\",\"description\":\"Test Description\",\"available\":true,\"owner\":{\"id\":1,\"name\":\"Test User\",\"email\":\"test@example.com\"},\"lastBooking\":null,\"comments\":[],\"nextBooking\":null,\"requestId\":1}";
        ItemDto itemDto = objectMapper.readValue(json, ItemDto.class);

        assertNotNull(itemDto);
        assertEquals(1L, itemDto.getId());
        assertEquals("Test Item", itemDto.getName());
        assertEquals("Test Description", itemDto.getDescription());
    }
}
