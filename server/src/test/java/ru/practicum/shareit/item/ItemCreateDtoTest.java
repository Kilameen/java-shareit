package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.item.dto.ItemCreateDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemCreateDtoTest {

    @Autowired
    private ObjectMapper json;

    @Test
    void testItemCreateDtoSerialization() throws IOException {
        ItemCreateDto itemCreateDto = ItemCreateDto.builder()
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .requestId(1L)
                .build();

        String jsonString = json.writeValueAsString(itemCreateDto);

        assertThat(jsonString).contains("\"name\":\"Test Item\"");
        assertThat(jsonString).contains("\"description\":\"Test Description\"");
        assertThat(jsonString).contains("\"available\":true");
        assertThat(jsonString).contains("\"requestId\":1");
    }

    @Test
    void testItemCreateDtoDeserialization() throws IOException {
        String jsonString = "{\"name\":\"Test Item\", \"description\":\"Test Description\", \"available\":true, \"requestId\":1}";

        ItemCreateDto itemCreateDto = json.readValue(jsonString, ItemCreateDto.class);

        assertThat(itemCreateDto.getName()).isEqualTo("Test Item");
        assertThat(itemCreateDto.getDescription()).isEqualTo("Test Description");
        assertThat(itemCreateDto.getAvailable()).isEqualTo(true);
        assertThat(itemCreateDto.getRequestId()).isEqualTo(1L);
    }
}