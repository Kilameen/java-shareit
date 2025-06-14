package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserMapperTest {

    @Test
    void toUserFullReturnsMappedObjectTest() {

        UserDto userDto = UserDto.builder().id(1L).name("Test").email("test@yandex.ru").build();

        User mappedUser = UserMapper.toUser(userDto);

        assertEquals(mappedUser.getId(), userDto.getId());
        assertEquals(mappedUser.getName(), userDto.getName());
        assertEquals(mappedUser.getEmail(), userDto.getEmail());
    }

    @Test
    void toUserReturnsMappedObjectWithNullFieldsTest() {

        UserDto userDto = UserDto.builder().id(null).name("").email(null).build();

        User mappedUser = UserMapper.toUser(userDto);

        assertNull(mappedUser.getId());
        assertEquals("", mappedUser.getName());
        assertNull(mappedUser.getEmail());
    }

    @Test
    void toUserReturnsMappedObjectWithPartialDataTest() {

        UserDto userDto = UserDto.builder()
                .id(1L).name("Test").email(null).build();

        User mappedUser = UserMapper.toUser(userDto);

        assertEquals(mappedUser.getId(), userDto.getId());
        assertEquals(mappedUser.getName(), userDto.getName());
        assertNull(mappedUser.getEmail());
    }
}