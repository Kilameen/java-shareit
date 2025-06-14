package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private ItemDto itemDto;
    private ItemCreateDto itemCreateDto;

    @BeforeEach
    void setUp() {
        itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Test Item");
        itemDto.setDescription("Test Description");
        itemDto.setAvailable(true);

        itemCreateDto = new ItemCreateDto();
        itemCreateDto.setName("Test Item");
        itemCreateDto.setDescription("Test Description");
        itemCreateDto.setAvailable(true);
    }

    @Test
    void createItemTest() {
        when(itemService.create(anyLong(), any(ItemCreateDto.class))).thenReturn(itemDto);
        ItemDto result = itemController.create(1L, itemCreateDto);
        assertEquals(itemDto, result);
    }

    @Test
    void updateItemTest() {
        when(itemService.update(anyLong(), anyLong(), any(ItemUpdateDto.class))).thenReturn(itemDto);
        ItemDto result = itemController.update(1L, 1L, new ItemUpdateDto());
        assertEquals(itemDto, result);
    }

    @Test
    void getItemByIdTest() {
        when(itemService.getItemDtoById(anyLong(), anyLong())).thenReturn(itemDto);
        ItemDto result = itemController.getItemById(1L, 1L);
        assertEquals(itemDto, result);
    }

    @Test
    void getItemsByUserIdTest() {
        when(itemService.getAllItemDtoByUserId(anyLong())).thenReturn(Collections.singletonList(itemDto));
        List<ItemDto> result = (List<ItemDto>) itemController.getItemsByUserId(1L);
        assertEquals(1, result.size());
        assertEquals(itemDto, result.get(0));
    }

    @Test
    void searchItemsTest() {
        when(itemService.searchItems(anyLong(), any(String.class))).thenReturn(Collections.singletonList(itemDto));
        List<ItemDto> result = itemController.searchItems(1L, "text");
        assertEquals(1, result.size());
        assertEquals(itemDto, result.get(0));
    }

    @Test
    void updateItemNotFoundTest() {
        when(itemService.update(anyLong(), anyLong(), any(ItemUpdateDto.class))).thenThrow(new NotFoundException("Вещь не найдена"));

        assertThrows(NotFoundException.class, () -> {
            itemController.update(1L, 999L, new ItemUpdateDto());
        });
    }
}