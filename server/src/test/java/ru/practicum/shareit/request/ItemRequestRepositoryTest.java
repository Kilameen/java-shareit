package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRequestRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @Test
    void findAllByRequesterIdWhenRequestsExistForUserThenReturnListOfRequests() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        entityManager.persist(user);

        ItemRequest request1 = new ItemRequest();
        request1.setRequester(user);
        request1.setDescription("Description 1");
        entityManager.persist(request1);

        ItemRequest request2 = new ItemRequest();
        request2.setRequester(user);
        request2.setDescription("Description 2");
        entityManager.persist(request2);

        User otherUser = new User();
        otherUser.setName("Other User");
        otherUser.setEmail("other@example.com");
        entityManager.persist(otherUser);

        ItemRequest request3 = new ItemRequest();
        request3.setRequester(otherUser);
        request3.setDescription("Description 3");
        entityManager.persist(request3);

        entityManager.flush();
        entityManager.clear();

        List<ItemRequest> requests = itemRequestRepository.findAllByRequesterId(user.getId());

        assertEquals(2, requests.size());
        assertTrue(requests.stream().allMatch(r -> r.getRequester().getId().equals(user.getId())));
    }

    @Test
    void findAllByRequesterId_whenNoRequestsExistForUser_thenReturnEmptyList() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        entityManager.persist(user);

        entityManager.flush();
        entityManager.clear();
        List<ItemRequest> requests = itemRequestRepository.findAllByRequesterId(user.getId());

        assertTrue(requests.isEmpty());
    }

    @Test
    void findAllByRequesterId_whenUserDoesNotExist_thenReturnEmptyList() {
        List<ItemRequest> requests = itemRequestRepository.findAllByRequesterId(999L);
        assertTrue(requests.isEmpty());
    }
}