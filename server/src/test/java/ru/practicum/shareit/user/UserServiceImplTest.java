package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder().name("Test").email("test@yandex.ru").build();
        user = UserMapper.toUser(userDto);
        user.setId(1L);
    }

    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto createdUserDto = userService.create(userDto);
        assertEquals(userDto.getName(), createdUserDto.getName());
        assertEquals(userDto.getEmail(), createdUserDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDto foundUserDto = userService.findUserById(1L);
        assertEquals(userDto.getName(), foundUserDto.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void createUserReturnUserDto() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto createdUserDto = userService.create(userDto);
        assertEquals(userDto.getName(), createdUserDto.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto userUpdateDto = UserDto.builder().name("Update").email("update@yandex.ru").build();
        UserDto updatedUserDto = userService.update(1L, userUpdateDto);
        assertEquals("Update", updatedUserDto.getName());
        assertEquals("update@yandex.ru", updatedUserDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserIfThrowNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.update(1L, userDto));
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        userService.delete(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void findUserByIdWhenUserFound() {
        Long userId = 1L;
        User expectedUser = User.builder().id(1L).name("Test").email("test@yandex.ru").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        UserDto expectedUserDto = UserMapper.toUserDto(expectedUser);

        UserDto actualUserDto = userService.findUserById(userId);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void findUserByIdWhenUserNotFound() {
        Long userId = 0L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException userNotFoundException = assertThrows(NotFoundException.class,
                () -> userService.findUserById(userId));

        assertEquals(userNotFoundException.getMessage(), "Пользователь с id " + userId + " не найден");
    }

    @Test
    void findAllUsersTest() {
        List<User> expectedUsers = List.of(new User());
        List<UserDto> expectedUserDto = expectedUsers.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<UserDto> actualUsersDto = userService.findAll();

        assertEquals(actualUsersDto.size(), 1);
        assertEquals(actualUsersDto, expectedUserDto);
    }
}