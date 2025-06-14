package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemRequestDtoTest {

    @Autowired
    private JacksonTester<ItemRequestCreateDto> itemRequestCreateDtoJacksonTester;

    @Autowired
    private JacksonTester<ItemRequestDto> itemRequestDtoJacksonTester;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    void testItemRequestCreateDtoSerialization() throws IOException {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime nowTruncated = now.truncatedTo(ChronoUnit.SECONDS);
        ItemRequestCreateDto itemRequestCreateDto = ItemRequestCreateDto.builder()
                .id(1L)
                .description("Test")
                .created(nowTruncated)
                .build();

        String jsonContent = itemRequestCreateDtoJacksonTester.write(itemRequestCreateDto).getJson();

        assertThat(jsonContent).contains("\"id\":1");
        assertThat(jsonContent).contains("\"description\":\"Test\"");
        assertThat(jsonContent).contains("\"created\":\"" + nowTruncated.format(FORMATTER) + "\"");
    }

    @Test
    void testItemRequestDtoDeserialization() throws IOException {
        String json = "{\"id\":1,\"description\":\"Test\",\"created\":\"2023-10-26T10:00:00\",\"items\":[]}";
        ItemRequestDto itemRequestDto = itemRequestDtoJacksonTester.parse(json).getObject();

        assertThat(itemRequestDto.getId()).isEqualTo(1L);
        assertThat(itemRequestDto.getDescription()).isEqualTo("Test");
        assertThat(itemRequestDto.getCreated()).isEqualTo(LocalDateTime.parse("2023-10-26T10:00:00", FORMATTER));
        assertThat(itemRequestDto.getItems()).isEmpty();
    }
}