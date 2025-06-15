package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentRepositoryTest {

    @Mock
    private CommentRepository commentRepository;

    private Comment comment1;
    private Comment comment2;
    private Item item1;
    private User user1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        item1 = Item.builder()
                .id(1L)
                .name("Hammer")
                .description("A sturdy hammer")
                .available(true)
                .owner(user1)
                .build();


        comment1 = Comment.builder()
                .id(1L)
                .text("Отличная вещь!")
                .item(item1)
                .author(user1)
                .created(LocalDateTime.now())
                .build();

        comment2 = Comment.builder()
                .id(2L)
                .text("Не очень удобно.")
                .item(item1)
                .author(user1)
                .created(LocalDateTime.now())
                .build();
    }

    @Test
    void findAllByItemId_existingItemId_returnsListOfComments() {
        Long itemId = item1.getId();
        List<Comment> expectedComments = List.of(comment1, comment2);
        when(commentRepository.findAllByItemId(itemId)).thenReturn(expectedComments);

        List<Comment> actualComments = commentRepository.findAllByItemId(itemId);

        assertEquals(expectedComments.size(), actualComments.size());
        assertEquals(expectedComments, actualComments);
        verify(commentRepository, times(1)).findAllByItemId(itemId); // Проверяем, что метод был вызван 1 раз
    }

    @Test
    void findAllByItemId_nonExistingItemId_returnsEmptyList() {
        Long itemId = 999L;
        when(commentRepository.findAllByItemId(itemId)).thenReturn(Collections.emptyList());

        List<Comment> actualComments = commentRepository.findAllByItemId(itemId);

        assertTrue(actualComments.isEmpty());
        verify(commentRepository, times(1)).findAllByItemId(itemId);
    }

    @Test
    void findAllByItemId_nullItemId_returnsEmptyList() {
         Long itemId = null;
        when(commentRepository.findAllByItemId(itemId)).thenReturn(Collections.emptyList());

        List<Comment> actualComments = commentRepository.findAllByItemId(itemId);

        assertTrue(actualComments.isEmpty());
        verify(commentRepository, times(1)).findAllByItemId(itemId);
    }

    @Test
    void findAllByItemId_itemIdIsZero_returnsEmptyList() {
        Long itemId = 0L;
        when(commentRepository.findAllByItemId(itemId)).thenReturn(Collections.emptyList());

        List<Comment> actualComments = commentRepository.findAllByItemId(itemId);

        assertTrue(actualComments.isEmpty());
        verify(commentRepository, times(1)).findAllByItemId(itemId);
    }

    @Test
    void save_validComment_returnsSavedCommentWithId() {
        Comment commentToSave = Comment.builder()
                .text("Новый комментарий")
                .item(item1)
                .author(user1)
                .build();

        Comment savedComment = Comment.builder()
                .id(3L)
                .text("Новый комментарий")
                .item(item1)
                .author(user1)
                .created(LocalDateTime.now())
                .build();

        when(commentRepository.save(commentToSave)).thenReturn(savedComment);

        Comment actualComment = commentRepository.save(commentToSave);

        assertEquals(savedComment.getId(), actualComment.getId());
        assertEquals(savedComment.getText(), actualComment.getText());
        assertEquals(savedComment.getItem(), actualComment.getItem());
        assertEquals(savedComment.getAuthor(), actualComment.getAuthor());
        verify(commentRepository, times(1)).save(commentToSave);
    }

    @Test
    void save_emptyTextComment_returnsSavedCommentWithId() {
        Comment commentToSave = Comment.builder()
                .text("")
                .item(item1)
                .author(user1)
                .build();

        Comment savedComment = Comment.builder()
                .id(3L)
                .text("")
                .item(item1)
                .author(user1)
                .created(LocalDateTime.now())
                .build();

        when(commentRepository.save(commentToSave)).thenReturn(savedComment);
        Comment actualComment = commentRepository.save(commentToSave);

        assertEquals(savedComment.getId(), actualComment.getId());
        assertEquals(savedComment.getText(), actualComment.getText());
        assertEquals(savedComment.getItem(), actualComment.getItem());
        assertEquals(savedComment.getAuthor(), actualComment.getAuthor());
        verify(commentRepository, times(1)).save(commentToSave);
    }

    @Test
    void save_nullItem_throwsException() {
        Comment commentToSave = Comment.builder()
                .text("Some text")
                .item(null)
                .author(user1)
                .build();

        when(commentRepository.save(commentToSave)).thenThrow(new IllegalArgumentException("Item cannot be null"));

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> commentRepository.save(commentToSave));
        verify(commentRepository, times(1)).save(commentToSave);
    }
}