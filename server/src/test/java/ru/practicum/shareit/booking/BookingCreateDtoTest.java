package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.Status;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingCreateDtoTest {

    @Autowired
    private JacksonTester<BookingCreateDto> json;

    @Test
    void testSerialize() throws IOException {
        BookingCreateDto bookingCreateDto = BookingCreateDto.builder()
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .itemId(1L)
                .bookerId(1L)
                .status(Status.WAITING)
                .build();

        var jsonContent = json.write(bookingCreateDto);

        assertThat(jsonContent).hasJsonPath("$.start");
        assertThat(jsonContent).hasJsonPath("$.end");
        assertThat(jsonContent).hasJsonPath("$.itemId");
        assertThat(jsonContent).hasJsonPath("$.bookerId");
        assertThat(jsonContent).hasJsonPath("$.status");
    }

    @Test
    void testDeserialize() throws IOException {
        String jsonString = "{\"start\":\"2024-10-27T10:00:00\",\"end\":\"2024-10-28T10:00:00\",\"itemId\":1,\"bookerId\":1,\"status\":\"WAITING\"}";
        BookingCreateDto bookingCreateDto = json.parseObject(jsonString);

        assertThat(bookingCreateDto).isNotNull();
        assertThat(bookingCreateDto.getItemId()).isEqualTo(1L);
        assertThat(bookingCreateDto.getBookerId()).isEqualTo(1L);
        assertThat(bookingCreateDto.getStatus()).isEqualTo(Status.WAITING);
    }
}