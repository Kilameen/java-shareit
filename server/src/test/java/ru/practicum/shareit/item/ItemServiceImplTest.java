package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User user;
    private Item item;
    private ItemCreateDto itemCreateDto;
    private ItemUpdateDto itemUpdateDto;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("Test").email("Test@yandex.ru").build();
        itemCreateDto = ItemCreateDto.builder().name("TestItem").description("For test").available(true).build();
        item = Item.builder().id(1L).name("TestItem").description("For test").available(true).owner(user).build();
        itemUpdateDto = ItemUpdateDto.builder().build();
    }

    @Test
    void createItemReturnItemDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto itemDto = itemService.create(1L, itemCreateDto);

        assertEquals(itemCreateDto.getName(), itemDto.getName());
        assertEquals(itemCreateDto.getDescription(), itemDto.getDescription());
        assertEquals(itemCreateDto.getAvailable(), itemDto.getAvailable());
    }

    @Test
    void createItemWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            itemService.create(1L, itemCreateDto);
        });

        assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
    }

    @Test
    void getItemDtoByIdItemNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().id(1L).build()));

        assertThrows(NotFoundException.class, () -> itemService.getItemDtoById(1L, 1L));
    }

    @Test
    void getItemByIdNotFoundTest() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().id(1L).build()));

        assertThrows(NotFoundException.class, () -> {
            itemService.getItemDtoById(1L, 1L);
        });
    }

    @Test
    void updateItemWhenItemNotFound() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        ItemUpdateDto itemUpdateDto = ItemUpdateDto.builder().build();

        assertThrows(NotFoundException.class, () -> itemService.update(1L, 1L, itemUpdateDto));
    }

    @Test
    void updateItemSuccess() {
        ItemUpdateDto itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setName("Updated");
        itemUpdateDto.setDescription("Updated Description");
        itemUpdateDto.setAvailable(false);

        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto updatedItemDto = itemService.update(1L, 1L, itemUpdateDto);

        assertEquals(itemUpdateDto.getName(), updatedItemDto.getName());
        assertEquals(itemUpdateDto.getDescription(), updatedItemDto.getDescription());
        assertEquals(itemUpdateDto.getAvailable(), updatedItemDto.getAvailable());
    }

    @Test
    void updateItemNoUpdates() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        ItemDto updatedItemDto = itemService.update(1L, 1L, itemUpdateDto);
        assertEquals(item.getName(), updatedItemDto.getName());
        assertEquals(item.getDescription(), updatedItemDto.getDescription());
        assertEquals(item.getAvailable(), updatedItemDto.getAvailable());
    }

    @Test
    void updateItemEmptyName() {
        itemUpdateDto.setName("");
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ItemDto updatedItemDto = itemService.update(1L, 1L, itemUpdateDto);

        assertEquals("", updatedItemDto.getName());
    }

    @Test
    void updateItemEmptyUpdateDtoNoChanges() {
        ItemUpdateDto emptyUpdateDto = ItemUpdateDto.builder().build();
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto updatedItemDto = itemService.update(1L, 1L, emptyUpdateDto);

        assertEquals(item.getName(), updatedItemDto.getName());
        assertEquals(item.getDescription(), updatedItemDto.getDescription());
        assertEquals(item.getAvailable(), updatedItemDto.getAvailable());
    }

    @Test
    void updateItemOnlyNameUpdated() {
        itemUpdateDto = ItemUpdateDto.builder().name("New Name").build();
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto updatedItemDto = itemService.update(1L, 1L, itemUpdateDto);

        assertEquals(itemUpdateDto.getName(), updatedItemDto.getName());
        assertEquals(item.getDescription(), updatedItemDto.getDescription());
        assertEquals(item.getAvailable(), updatedItemDto.getAvailable());
    }

    @Test
    void updateItemOnlyDescriptionUpdated() {
        itemUpdateDto = ItemUpdateDto.builder().description("New Description").build();
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto updatedItemDto = itemService.update(1L, 1L, itemUpdateDto);

        assertEquals(item.getName(), updatedItemDto.getName());
        assertEquals(itemUpdateDto.getDescription(), updatedItemDto.getDescription());
        assertEquals(item.getAvailable(), updatedItemDto.getAvailable());
    }

    @Test
    void updateItemOnlyAvailableUpdated() {
        itemUpdateDto = ItemUpdateDto.builder().available(false).build();
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto updatedItemDto = itemService.update(1L, 1L, itemUpdateDto);

        assertEquals(item.getName(), updatedItemDto.getName());
        assertEquals(item.getDescription(), updatedItemDto.getDescription());
        assertEquals(itemUpdateDto.getAvailable(), updatedItemDto.getAvailable());
    }

    @Test
    void getItemDtoByIdTest() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.findAllByItemId(1L)).thenReturn(Collections.emptyList());
        when(bookingRepository.findAllByItemAndStatusOrderByStartAsc(any(), any())).thenReturn(Collections.emptyList());

        ItemDto itemDto = itemService.getItemDtoById(1L, 1L);

        assertEquals(item.getId(), itemDto.getId());
        assertEquals(item.getName(), itemDto.getName());
        assertEquals(item.getDescription(), itemDto.getDescription());
        assertEquals(item.getAvailable(), itemDto.getAvailable());
    }
}