package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser() {
        UserDto userDto = UserDto.builder().name("Test").email("test@yandex.ru").build();
        UserDto createdUserDto = UserDto.builder().id(1L).name("Test").email("test@yandex.ru").build();

        when(userService.create(userDto)).thenReturn(createdUserDto);

        UserDto result = userController.create(userDto);

        assertEquals(createdUserDto, result);
        verify(userService, times(1)).create(userDto);
    }

    @Test
    void updateUser() {
        Long userId = 1L;
        UserDto userDto = UserDto.builder().name("Updated").email("updated@yandex.ru").build();
        UserDto updatedUserDto = UserDto.builder().id(userId).name("Updated").email("updated@yandex.ru").build();

        when(userService.update(userId, userDto)).thenReturn(updatedUserDto);

        UserDto result = userController.update(userId, userDto);

        assertEquals(updatedUserDto, result);
        verify(userService, times(1)).update(userId, userDto);
    }

    @Test
    void getUserById() {
        Long userId = 1L;
        UserDto userDto = UserDto.builder().id(userId).name("Test").email("test@yandex.ru").build();

        when(userService.findUserById(userId)).thenReturn(userDto);

        UserDto result = userController.getUserById(userId);

        assertEquals(userDto, result);
        verify(userService, times(1)).findUserById(userId);
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        userController.deleteUser(userId);

        verify(userService, times(1)).delete(userId);
    }

    @Test
    void getAllUsers() {
        UserDto userDto = UserDto.builder().id(1L).name("Test").email("test@yandex.ru").build();
        List<UserDto> userDtoList = Collections.singletonList(userDto);

        when(userService.findAll()).thenReturn(userDtoList);

        List<UserDto> result = userController.getAllUsers();

        assertEquals(userDtoList, result);
        verify(userService, times(1)).findAll();
    }
}