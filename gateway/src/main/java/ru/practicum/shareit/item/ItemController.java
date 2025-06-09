package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @Valid @RequestBody ItemCreateDto itemDto) {
        log.info("POST запрос на создание новой вещи: {} от пользователя c id: {}", itemDto, userId);
        return itemClient.create(userId,itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestBody ItemUpdateDto itemDto,
                                         @PathVariable("itemId") Long itemId) {
        log.info("PATCH запрос на обновление вещи id: {} пользователя c id: {}", itemId, userId);
        return itemClient.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findItemByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET запрос на получение вещи");
        return itemClient.findItemByUserId(userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET запрос на получение всех вещей пользователя c id: {}", userId);
        return itemClient.findAll(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @RequestParam String text) {
        log.info("GET запрос на поиск всех вещей c текстом: {}", text);
        return itemClient.searchItems(userId, text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @Validated @RequestBody CommentRequestDto commentDto,
                                                @PathVariable Long itemId) {
        return itemClient.createComment(userId, commentDto, itemId);
    }
}