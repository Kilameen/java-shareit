package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.user.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Test
    void createWhenUserNotFound() {
        Long userId = 1L;
        ItemRequestCreateDto itemRequestCreateDto = new ItemRequestCreateDto();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.create(userId, itemRequestCreateDto));
    }

    @Test
    void getUserRequestsWhenUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.getUserRequests(userId));
    }

    @Test
    void getAllRequestsWhenUserNotFound() {
        Long userId = 1L;
        Integer from = 0;
        Integer size = 10;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.getAllRequests(userId, from, size));
    }

    @Test
    void getAllRequestByIdWhenRequestNotFound() {
        Long requestId = 1L;
        when(itemRequestRepository.findById(requestId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.getAllRequestById(requestId));
    }
}