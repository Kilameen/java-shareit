package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.RequestClient;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {

    @Mock
    private RequestClient requestClient;

    @InjectMocks
    private ItemRequestController itemRequestController;

    @Test
    void createItemRequestReturnResponseFromClient() {
        Long userId = 1L;
        ItemRequestCreateDto requestDto = new ItemRequestCreateDto();
        requestDto.setDescription("Need a good Item");
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Запрос на создание", HttpStatus.OK);
        when(requestClient.create(userId, requestDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = itemRequestController.create(userId, requestDto);

        assertEquals(expectedResponse, actualResponse);
        verify(requestClient, times(1)).create(userId, requestDto);
    }

    @Test
    void getUserRequestsReturnResponseFromClient() {
        Long userId = 1L;
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Запрос Пользователя", HttpStatus.OK);
        when(requestClient.findUserRequests(userId)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = itemRequestController.getUserRequests(userId);

        assertEquals(expectedResponse, actualResponse);
        verify(requestClient, times(1)).findUserRequests(userId);
    }

    @Test
    void getAllRequestsReturnResponseFromClient() {
        Long userId = 1L;
        Integer from = 0;
        Integer size = 10;
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Все запросы", HttpStatus.OK);
        when(requestClient.findAllRequests(userId, from, size)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = itemRequestController.getAllRequests(userId, from, size);

        assertEquals(expectedResponse, actualResponse);
        verify(requestClient, times(1)).findAllRequests(userId, from, size);
    }

    @Test
    void getRequestByIdReturnResponseFromClient() {
        Long requestId = 1L;
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Запрос по ID", HttpStatus.OK);
        when(requestClient.findRequestById(requestId)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = itemRequestController.findRequestById(requestId);

        assertEquals(expectedResponse, actualResponse);
        verify(requestClient, times(1)).findRequestById(requestId);
    }
}