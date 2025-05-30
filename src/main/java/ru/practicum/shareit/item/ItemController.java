package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.Marker;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public NewItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody ItemDto itemDto) {
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public NewItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId, @RequestBody ItemDto itemDto) {
        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public NewItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @PathVariable("itemId")
                                  Long itemId) {
        return itemService.getItemDtoById(userId, itemId);
    }

    @GetMapping
    public Collection<NewItemDto> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllItemDtoByUserId(userId);
    }

    @GetMapping("/search")
    public List<NewItemDto> searchItems(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam String text) {
        return itemService.searchItems(userId, text);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }

    @PostMapping("/{itemId}/comment")
    public NewCommentDto createComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @Validated @RequestBody CommentDto commentDto,
                                       @PathVariable Long itemId) {
        return itemService.createComment(userId, commentDto, itemId);
    }
}

