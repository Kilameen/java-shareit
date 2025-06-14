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
}