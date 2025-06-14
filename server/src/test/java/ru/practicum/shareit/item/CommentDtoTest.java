package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.CommentDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CommentDtoTest {

    @Autowired
    private JacksonTester<CommentDto> json;

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Test
    void testCommentDto() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .itemId(10L)
                .text("Test comment")
                .authorName("Test")
                .created(now)
                .build();

        JsonContent<CommentDto> result = json.write(commentDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.itemId");
        assertThat(result).hasJsonPath("$.text");
        assertThat(result).hasJsonPath("$.authorName");
        assertThat(result).hasJsonPath("$.created");

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(10);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("Test comment");
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("Test");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(now.format(FORMATTER));
    }

    @Test
    void testDeserializeCommentDto() throws IOException {
        String jsonString = "{\"id\":1,\"itemId\":10,\"text\":\"Test comment\",\"authorName\":\"Test\",\"created\":\"2024-10-27T10:00:00\"}";

        CommentDto commentDto = json.parse(jsonString).getObject();

        assertThat(commentDto.getId()).isEqualTo(1L);
        assertThat(commentDto.getItemId()).isEqualTo(10L);
        assertThat(commentDto.getText()).isEqualTo("Test comment");
        assertThat(commentDto.getAuthorName()).isEqualTo("Test");
        assertThat(commentDto.getCreated()).isEqualTo(LocalDateTime.parse("2024-10-27T10:00:00"));
    }
}