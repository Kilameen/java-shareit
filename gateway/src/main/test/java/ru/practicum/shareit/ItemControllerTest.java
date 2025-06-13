package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemClient itemClient;

    @InjectMocks
    private ItemController itemController;

    @Test
    void createItemReturnResponseFromClient() {
        Long userId = 1L;
        ItemCreateDto itemDto = new ItemCreateDto();
        when(itemClient.create(userId, itemDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        itemController.create(userId, itemDto);

        verify(itemClient, times(1)).create(userId, itemDto);
    }

    @Test
    void updateItemReturnResponseFromClient() {
        Long userId = 1L;
        Long itemId = 2L;
        ItemUpdateDto itemDto = new ItemUpdateDto();
        when(itemClient.update(userId, itemId, itemDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        itemController.update(userId, itemDto, itemId);

        verify(itemClient, times(1)).update(userId, itemId, itemDto);
    }

    @Test
    void getItemByIdReturnResponseFromClient() {
        Long userId = 1L;
        Long itemId = 2L;
        when(itemClient.findItemById(itemId, userId)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        itemController.findItemById(userId, itemId);

        verify(itemClient, times(1)).findItemById(itemId, userId);
    }

    @Test
    void getAllItemsReturnResponseFromClient() {
        Long userId = 1L;
        when(itemClient.findAll(userId)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        itemController.findAll(userId);
        verify(itemClient, times(1)).findAll(userId);
    }

    @Test
    void searchItemsReturnResponseFromClient() {
        Long userId = 1L;
        String text = "test";
        when(itemClient.searchItems(userId, text)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        itemController.searchItems(userId, text);
        verify(itemClient, times(1)).searchItems(userId, text);
    }

    @Test
    void createCommentReturnResponseFromClient() {
        Long userId = 1L;
        Long itemId = 2L;
        CommentRequestDto commentDto = new CommentRequestDto();
        when(itemClient.createComment(userId, commentDto, itemId)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        itemController.createComment(userId, commentDto, itemId);

        verify(itemClient, times(1)).createComment(userId, commentDto, itemId);
    }
}