package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {

    @Test
    public void testToComment() {
        CommentCreateDto commentCreateDto = new CommentCreateDto("Test comment");
        Item item = new Item(1L, "Test item", "description", true, null, null);
        User author = new User(1L, "Test author", "test@yandex.ru");

        Comment comment = CommentMapper.toComment(commentCreateDto, item, author);

        assertEquals("Test comment", comment.getText());
        assertEquals(item, comment.getItem());
        assertEquals(author, comment.getAuthor());
    }

    @Test
    public void testToCommentDto() {
        User author = new User(1L, "Test author", "test@yandex.ru");
        Item item = new Item(1L, "Test item", "description", true, null, null);
        Comment comment = new Comment(1L, "Test comment", item, author, LocalDateTime.now());

        CommentDto commentDto = CommentMapper.toCommentDto(comment);

        assertEquals("Test comment", commentDto.getText());
        assertEquals("Test author", commentDto.getAuthorName());
        assertEquals(item.getId(), commentDto.getItemId());
    }
}