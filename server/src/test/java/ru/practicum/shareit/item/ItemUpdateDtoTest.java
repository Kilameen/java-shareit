package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemUpdateDtoTest {

    @Autowired
    private ObjectMapper json;

    @Test
    void testItemUpdateDtoSerialization() throws IOException {
        ItemUpdateDto itemUpdateDto = ItemUpdateDto.builder()
                .name("New Item Name")
                .description("New Item Description")
                .available(true)
                .requestId(1L)
                .build();

        String jsonString = json.writeValueAsString(itemUpdateDto);

        assertThat(jsonString).contains("\"name\":\"New Item Name\"");
        assertThat(jsonString).contains("\"description\":\"New Item Description\"");
        assertThat(jsonString).contains("\"available\":true");
        assertThat(jsonString).contains("\"requestId\":1");
    }

    @Test
    void testItemUpdateDtoDeserialization() throws IOException {
        String jsonString = "{\"name\":\"Updated Name\", \"description\":\"Updated Description\", \"available\":false, \"requestId\":2}";

        ItemUpdateDto itemUpdateDto = json.readValue(jsonString, ItemUpdateDto.class);

        assertThat(itemUpdateDto.getName()).isEqualTo("Updated Name");
        assertThat(itemUpdateDto.getDescription()).isEqualTo("Updated Description");
        assertThat(itemUpdateDto.getAvailable()).isFalse();
        assertThat(itemUpdateDto.getRequestId()).isEqualTo(2L);
    }

    @Test
    void testItemUpdateDtoSerializationWithNullValues() throws IOException {
        ItemUpdateDto itemUpdateDto = ItemUpdateDto.builder()
                .name(null)
                .description("Description")
                .available(null)
                .requestId(null)
                .build();

        String jsonString = json.writeValueAsString(itemUpdateDto);
        assertThat(jsonString).contains("\"description\":\"Description\"");
    }

    @Test
    void testItemUpdateDtoDeserializationWithEmptyValues() throws IOException {
        String jsonString = "{\"name\":\"\", \"description\":\"\", \"available\":null, \"requestId\":null}";
        ItemUpdateDto itemUpdateDto = json.readValue(jsonString, ItemUpdateDto.class);

        assertThat(itemUpdateDto.getName()).isEqualTo("");
        assertThat(itemUpdateDto.getDescription()).isEqualTo("");
        assertThat(itemUpdateDto.getAvailable()).isNull();
        assertThat(itemUpdateDto.getRequestId()).isNull();
    }

    @Test
    void testItemUpdateDtoDeserialization_emptyJson() throws IOException {
        String jsonString = "{}";

        ItemUpdateDto itemUpdateDto = json.readValue(jsonString, ItemUpdateDto.class);

        assertThat(itemUpdateDto.getName()).isNull();
        assertThat(itemUpdateDto.getDescription()).isNull();
        assertThat(itemUpdateDto.getAvailable()).isNull();
        assertThat(itemUpdateDto.getRequestId()).isNull();
    }

    @Test
    void testItemUpdateDtoSerialization_emptyStringValues() throws IOException {
        ItemUpdateDto itemUpdateDto = ItemUpdateDto.builder()
                .name("")
                .description("")
                .build();

        String jsonString = json.writeValueAsString(itemUpdateDto);

        assertThat(jsonString).contains("\"name\":\"\"");
        assertThat(jsonString).contains("\"description\":\"\"");
    }

    @Test
    void testItemUpdateDtoDeserialization_booleanValues() throws IOException {
        String jsonString = "{\"available\":true}";
        ItemUpdateDto itemUpdateDto = json.readValue(jsonString, ItemUpdateDto.class);

        assertThat(itemUpdateDto.getAvailable()).isTrue();

        jsonString = "{\"available\":false}";
        itemUpdateDto = json.readValue(jsonString, ItemUpdateDto.class);

        assertThat(itemUpdateDto.getAvailable()).isFalse();
    }
}