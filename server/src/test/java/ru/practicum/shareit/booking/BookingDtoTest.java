package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingDtoTest {

    @Autowired
    private JacksonTester<BookingDto> bookingDtoJacksonTester;

    @Autowired
    private JacksonTester<BookingCreateDto> bookingCreateDtoJacksonTester;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Test
    void testBookingDtoSerialization() throws Exception {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);

        BookingDto bookingDto = BookingDto.builder()
                .id(1L)
                .start(start)
                .end(end)
                .item(new ItemDto(1L, "Item", "Description", true, null, null, null, null, 1L))
                .booker(new UserDto(1L, "User", "test@yandex.ru"))
                .status(Status.WAITING)
                .build();

        String jsonContent = bookingDtoJacksonTester.write(bookingDto).getJson();

        assertThat(jsonContent).contains("\"id\":1");
        assertThat(jsonContent).contains("\"start\":\"" + start.format(FORMATTER) + "\"");
        assertThat(jsonContent).contains("\"end\":\"" + end.format(FORMATTER) + "\"");
        assertThat(jsonContent).contains("\"status\":\"WAITING\"");
    }

    @Test
    void testBookingCreateDtoDeserialization() throws Exception {

        String jsonContent = "{\"start\":\"2024-01-01T10:00:00\",\"end\":\"2024-01-01T11:00:00\",\"itemId\":1,\"bookerId\":2,\"status\":\"WAITING\"}";

        BookingCreateDto bookingCreateDto = bookingCreateDtoJacksonTester.parse(jsonContent).getObject();

        assertThat(bookingCreateDto.getStart()).isEqualTo(LocalDateTime.parse("2024-01-01T10:00:00"));
        assertThat(bookingCreateDto.getItemId()).isEqualTo(1L);
        assertThat(bookingCreateDto.getStatus()).isEqualTo(Status.WAITING);
    }
}
