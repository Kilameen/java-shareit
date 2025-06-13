package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.UserClient;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private UserController userController;

    @Test
    void createReturnResponseFromClient() {
        UserRequestDto userDto = new UserRequestDto();
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Пользователь создан", HttpStatus.CREATED);

        when(userClient.create(userDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = userController.create(userDto);

        assertEquals(expectedResponse, actualResponse);
        verify(userClient, times(1)).create(userDto);
    }

    @Test
    void updateReturnResponseFromClient() {
        Long userId = 1L;
        UserRequestDto userDto = new UserRequestDto();
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Пользователь обновлен", HttpStatus.OK);
        when(userClient.update(userId, userDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = userController.update(userDto, userId);

        assertEquals(expectedResponse, actualResponse);
        verify(userClient, times(1)).update(userId, userDto);
    }

    @Test
    void findAllReturnResponseFromClient() {
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Найденные пользователи", HttpStatus.OK);
        when(userClient.findAll()).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = userController.findAll();

        assertEquals(expectedResponse, actualResponse);
        verify(userClient, times(1)).findAll();
    }

    @Test
    void findByIdReturnResponseFromClient() {
        Long userId = 1L;
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Пользователь найден", HttpStatus.OK);
        when(userClient.findById(userId)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = userController.findById(userId);

        assertEquals(expectedResponse, actualResponse);
        verify(userClient, times(1)).findById(userId);
    }

    @Test
    void deleteReturnResponseFromClient() {
        Long userId = 1L;
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Пользователь удален", HttpStatus.OK);
        when(userClient.deleteById(userId)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = userController.delete(userId);

        assertEquals(expectedResponse, actualResponse);
        verify(userClient, times(1)).deleteById(userId);
    }
}