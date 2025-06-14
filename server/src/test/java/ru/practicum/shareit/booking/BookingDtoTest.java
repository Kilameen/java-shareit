package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookingDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testSerializeBookingDto() throws Exception {
        BookingDto bookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .item(new ItemDto())
                .booker(new UserDto())
                .status(Status.WAITING)
                .build();

        String json = objectMapper.writeValueAsString(bookingDto);
        assertNotNull(json);
    }

    @Test
    public void testDeserializeBookingDto() throws Exception {
        String json = "{\"id\":1,\"start\":\"2024-01-01T10:00:00\",\"end\":\"2024-01-02T10:00:00\",\"item\":{},\"booker\":{},\"status\":\"WAITING\"}";

        BookingDto bookingDto = objectMapper.readValue(json, BookingDto.class);

        assertNotNull(bookingDto);
        assertEquals(1L, bookingDto.getId());
        assertEquals(Status.WAITING, bookingDto.getStatus());
    }
}