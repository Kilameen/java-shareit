package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    void findByOwnerWhenItemsExistForOwnerThenReturnListOfItems() {
        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setDescription("Description 1");
        item1.setAvailable(true);
        item1.setOwner(user);
        entityManager.persist(item1);

        Item item2 = new Item();
        item2.setName("Item 2");
        item2.setDescription("Description 2");
        item2.setAvailable(true);
        item2.setOwner(user);
        entityManager.persist(item2);

        List<Item> items = itemRepository.findByOwner(user);
        assertEquals(2, items.size());
    }

    @Test
    void findByOwnerWhenNoItemsExistForOwnerThenReturnEmptyList() {
        User otherUser = new User();
        otherUser.setName("Other User");
        otherUser.setEmail("other@yandex.ru");
        entityManager.persist(otherUser);

        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setDescription("Description 1");
        item1.setAvailable(true);
        item1.setOwner(otherUser);
        entityManager.persist(item1);

        List<Item> items = itemRepository.findByOwner(user);
        assertTrue(items.isEmpty());
    }

    @Test
    void searchItemsWhenTextMatchesNameOrDescriptionThenReturnListOfItems() {
        Item item1 = new Item();
        item1.setName("Item with keyword");
        item1.setDescription("Description 1");
        item1.setAvailable(true);
        item1.setOwner(user);
        entityManager.persist(item1);

        Item item2 = new Item();
        item2.setName("Item 2");
        item2.setDescription("Description contains keyword");
        item2.setAvailable(true);
        item2.setOwner(user);
        entityManager.persist(item2);

        List<Item> items = itemRepository.searchItems("keyword");
        assertEquals(2, items.size());
    }

    @Test
    void searchItemsWhenTextDoesNotMatchThenReturnEmptyList() {
        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setDescription("Description 1");
        item1.setAvailable(true);
        item1.setOwner(user);
        entityManager.persist(item1);

        List<Item> items = itemRepository.searchItems("nonexistentkeyword");
        assertTrue(items.isEmpty());
    }

    @Test
    void findByRequestIdWhenNoItemsExistForRequestThenReturnEmptyList() {
        List<Item> items = itemRepository.findByRequestId(999L);
        assertTrue(items.isEmpty());
    }

    @Test
    void searchItemsWhenItemNotAvailableThenReturnEmptyList() {
        Item item1 = new Item();
        item1.setName("Item with keyword");
        item1.setDescription("Description 1");
        item1.setAvailable(false);
        item1.setOwner(user);
        entityManager.persist(item1);

        List<Item> items = itemRepository.searchItems("keyword");
        assertTrue(items.isEmpty());
    }

    @Test
    void searchItemsEmptySearchTextReturnsAllAvailableItems() {
        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setDescription("Description 1");
        item1.setAvailable(true);
        item1.setOwner(user);
        entityManager.persist(item1);

        Item item2 = new Item();
        item2.setName("Item 2");
        item2.setDescription("Description 2");
        item2.setAvailable(true);
        item2.setOwner(user);
        entityManager.persist(item2);

        List<Item> items = itemRepository.searchItems("");
    }
}