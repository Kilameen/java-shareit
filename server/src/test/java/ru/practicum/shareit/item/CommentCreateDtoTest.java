package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.CommentCreateDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CommentCreateDtoTest {

    @Autowired
    private JacksonTester<CommentCreateDto> json;

    @Test
    void testCommentCreateDtoSerialization() throws IOException {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setText("Test comment");
        var jsonOutput = json.write(commentCreateDto);
        assertThat(jsonOutput).hasJsonPath("$.text");
        assertThat(jsonOutput).extractingJsonPathStringValue("$.text").isEqualTo("Test comment");
    }

    @Test
    void testCommentCreateDtoDeserialization() throws IOException {
        String jsonContent = "{\"text\":\"Test comment\"}";
        CommentCreateDto commentCreateDto = json.parse(jsonContent).getObject();
        assertThat(commentCreateDto.getText()).isEqualTo("Test comment");
    }
}