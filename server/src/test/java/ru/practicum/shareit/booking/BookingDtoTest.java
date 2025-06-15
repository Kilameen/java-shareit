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

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testDeserializeBookingDtoWithNullValues() throws Exception {
        String json = "{\"id\":null,\"start\":null,\"end\":null,\"item\":null,\"booker\":null,\"status\":null}";
        BookingDto bookingDto = objectMapper.readValue(json, BookingDto.class);

        assertNotNull(bookingDto, "Объект BookingDto не должен быть null");
        assertNull(bookingDto.getId(), "ID должен быть null");
        assertNull(bookingDto.getStart(), "Start должен быть null");
        assertNull(bookingDto.getEnd(), "End должен быть null");
        assertNull(bookingDto.getItem(), "Item должен быть null");
        assertNull(bookingDto.getBooker(), "Booker должен быть null");
        assertNull(bookingDto.getStatus(), "Status должен быть null");
    }

    @Test
    void testBookingDtoBuilder() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);

        BookingDto bookingDto = BookingDto.builder()
                .id(2L)
                .start(start)
                .end(end)
                .status(Status.APPROVED)
                .build();

        assertNotNull(bookingDto);
        assertEquals(2L, bookingDto.getId());
        assertEquals(start, bookingDto.getStart());
        assertEquals(end, bookingDto.getEnd());
        assertEquals(Status.APPROVED, bookingDto.getStatus());
    }

    @Test
    void testEqualsAndHashCode() {
        BookingDto bookingDto1 = BookingDto.builder().id(1L).status(Status.WAITING).build();
        BookingDto bookingDto2 = BookingDto.builder().id(1L).status(Status.WAITING).build();
        BookingDto bookingDto3 = BookingDto.builder().id(2L).status(Status.REJECTED).build();

        assertEquals(bookingDto1, bookingDto2);
        assertNotEquals(bookingDto1, bookingDto3);
        assertEquals(bookingDto1.hashCode(), bookingDto2.hashCode());
        assertNotEquals(bookingDto1.hashCode(), bookingDto3.hashCode());
    }

    @Test
    void testBookingDtoToString() {
        BookingDto bookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .status(Status.WAITING)
                .build();

        assertNotNull(bookingDto.toString());
        assertFalse(bookingDto.toString().isEmpty());
    }

    @Test
    void testStatusEnumValues() {
        assertEquals("WAITING", Status.WAITING.toString());
        assertEquals("APPROVED", Status.APPROVED.toString());
        assertEquals("REJECTED", Status.REJECTED.toString());
        assertEquals("CANCELED", Status.CANCELED.toString());
    }

    @Test
    void testBookingDtoNoArgsConstructor() {
        BookingDto bookingDto = new BookingDto();
        assertNotNull(bookingDto);
    }

    @Test
    void testSerializeDeserializeWithEmptyItemAndUser() throws Exception {
        BookingDto bookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .item(ItemDto.builder().build())
                .booker(UserDto.builder().build())
                .build();

        String json = objectMapper.writeValueAsString(bookingDto);
        BookingDto deserializedDto = objectMapper.readValue(json, BookingDto.class);

        assertEquals(bookingDto, deserializedDto);
    }

    @Test
    void testSerializeDeserializeWithSpecificDateTimes() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 5, 15, 12, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 5, 20, 12, 0, 0);

        BookingDto bookingDto = BookingDto.builder()
                .id(1L)
                .start(start)
                .end(end)
                .item(ItemDto.builder().id(1L).name("Test Item").build())
                .booker(UserDto.builder().id(1L).name("Test User").build())
                .status(Status.APPROVED)
                .build();

        String json = objectMapper.writeValueAsString(bookingDto);
        BookingDto deserializedDto = objectMapper.readValue(json, BookingDto.class);

        assertEquals(bookingDto, deserializedDto);
    }

    @Test
    void testSerializeDeserializeWithEdgeCases() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BookingDto bookingDto = BookingDto.builder()
                .id(Long.MAX_VALUE)
                .start(now.minusYears(100))
                .end(now.plusYears(100))
                .item(ItemDto.builder().id(Long.MAX_VALUE).name("VeryLongItemName").description("VeryLongDescription").available(true).build())
                .booker(UserDto.builder().id(Long.MAX_VALUE).name("VeryLongUserName").email("verylongemail@yandex.ru").build())
                .status(Status.REJECTED)
                .build();

        String json = objectMapper.writeValueAsString(bookingDto);
        BookingDto deserializedDto = objectMapper.readValue(json, BookingDto.class);

        assertEquals(bookingDto, deserializedDto);
    }
}