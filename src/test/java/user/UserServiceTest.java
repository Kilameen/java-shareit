package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        user1 = User.builder().name("testingName").email("testing@yandex.ru").build();
        user2 = User.builder().name("testingName2").email("testingtwo@yandex.ru").build();
    }

    @Test
    void createUserTest() {
        User createdUser = userService.create(user1);
        assertNotNull(createdUser.getId());
        assertEquals(user1.getName(), createdUser.getName());
        assertEquals(user1.getEmail(), createdUser.getEmail());
    }

    @Test
    void emailIsDuplicatedTest() {
        userService.create(user1);
        User userWithSameEmail = User.builder().name("testingNewName").email("testing@yandex.ru").build();
        assertThrows(DuplicatedDataException.class, () -> userService.create(userWithSameEmail));
    }

    @Test
    void updateUserTest() {
        User createdUser = userService.create(user1);
        User userUpdate = User.builder().id(createdUser.getId()).name("newName").email("updatedTesting@yandex.ru").build();
        User updatedUser = userService.update(userUpdate);
        assertEquals("newName", updatedUser.getName());
        assertEquals("updatedTesting@yandex.ru", updatedUser.getEmail());
    }

    @Test
    void userNotFoundTest() {
        User userUpdate = User.builder().id(999L).name("newName").email("updatedTesting@yandex.ru").build();
        assertThrows(NotFoundException.class, () -> userService.update(userUpdate));
    }

    @Test
    void returnAllUsersTest() {
        userService.create(user1);
        userService.create(user2);
        Collection<User> allUsers = userService.findAll();
        assertEquals(2, allUsers.size());
    }

    @Test
    void returnUserWhenFoundTest() {
        User createdUser = userService.create(user1);
        User foundUser = userService.findUserById(createdUser.getId());
        assertEquals(createdUser.getId(), foundUser.getId());
    }

    @Test
    void returnNullWhenNotFoundTest() {
        User foundUser = userService.findUserById(999L);
        assertNull(foundUser);
    }

    @Test
    void deleteUserTest() {
        User createdUser = userService.create(user1);
        userService.delete(createdUser.getId());
        assertNull(userService.findUserById(createdUser.getId()));
    }
}
