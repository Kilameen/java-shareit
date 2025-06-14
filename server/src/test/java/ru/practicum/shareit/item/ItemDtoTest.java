package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemDtoTest {

    @Autowired
    private JacksonTester<ItemDto> itemDtoJsonTester;

    @Autowired
    private JacksonTester<ItemCreateDto> itemCreateDtoJsonTester;

    @Autowired
    private JacksonTester<ItemUpdateDto> itemUpdateDtoJsonTester;

    @Test
    void testItemDtoSerialization() throws IOException {
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .owner(new UserDto())
                .comments(List.of())
                .requestId(2L)
                .build();

        var jsonContent = itemDtoJsonTester.write(itemDto);

        assertThat(jsonContent).hasJsonPath("$.id");
        assertThat(jsonContent).hasJsonPath("$.name");
        assertThat(jsonContent).hasJsonPath("$.description");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.id")
                .isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.name")
                .isEqualTo("Test Item");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description")
                .isEqualTo("Test Description");
    }

    @Test
    void testItemCreateDtoSerialization() throws IOException {
        ItemCreateDto itemCreateDto = ItemCreateDto.builder()
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .requestId(2L)
                .build();

        var jsonContent = itemCreateDtoJsonTester.write(itemCreateDto);

        assertThat(jsonContent).hasJsonPath("$.name");
        assertThat(jsonContent).hasJsonPath("$.description");
        assertThat(jsonContent).extractingJsonPathStringValue("$.name")
                .isEqualTo("Test Item");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description")
                .isEqualTo("Test Description");
    }

    @Test
    void testItemUpdateDtoSerialization() throws IOException {
        ItemUpdateDto itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setName("Test Item");
        itemUpdateDto.setDescription("Test Description");
        itemUpdateDto.setAvailable(true);
        itemUpdateDto.setRequestId(2L);

        var jsonContent = itemUpdateDtoJsonTester.write(itemUpdateDto);

        assertThat(jsonContent).hasJsonPath("$.name");
        assertThat(jsonContent).hasJsonPath("$.description");
        assertThat(jsonContent).extractingJsonPathStringValue("$.name")
                .isEqualTo("Test Item");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description")
                .isEqualTo("Test Description");
    }

    @Test
    void testItemDtoDeserialization() throws IOException {
        String json = "{\"id\":1,\"name\":\"Test Item\",\"description\":\"Test Description\",\"available\":true,\"owner\":{\"id\":null,\"name\":null,\"email\":null},\"comments\":[],\"requestId\":2}";
        ItemDto itemDto = itemDtoJsonTester.parseObject(json);

        assertThat(itemDto.getId()).isEqualTo(1L);
        assertThat(itemDto.getName()).isEqualTo("Test Item");
        assertThat(itemDto.getDescription()).isEqualTo("Test Description");
        assertThat(itemDto.getAvailable()).isEqualTo(true);
        assertThat(itemDto.getRequestId()).isEqualTo(2L);
    }

    @Test
    void testItemCreateDtoDeserialization() throws IOException {
        String json = "{\"name\":\"Test Item\",\"description\":\"Test Description\",\"available\":true,\"requestId\":2}";
        ItemCreateDto itemCreateDto = itemCreateDtoJsonTester.parseObject(json);

        assertThat(itemCreateDto.getName()).isEqualTo("Test Item");
        assertThat(itemCreateDto.getDescription()).isEqualTo("Test Description");
        assertThat(itemCreateDto.getAvailable()).isEqualTo(true);
        assertThat(itemCreateDto.getRequestId()).isEqualTo(2L);
    }

    @Test
    void testItemUpdateDtoDeserialization() throws IOException {
        String json = "{\"name\":\"Test Item\",\"description\":\"Test Description\",\"available\":true,\"requestId\":2}";
        ItemUpdateDto itemUpdateDto = itemUpdateDtoJsonTester.parseObject(json);

        assertThat(itemUpdateDto.getName()).isEqualTo("Test Item");
        assertThat(itemUpdateDto.getDescription()).isEqualTo("Test Description");
        assertThat(itemUpdateDto.getAvailable()).isEqualTo(true);
        assertThat(itemUpdateDto.getRequestId()).isEqualTo(2L);
    }
}