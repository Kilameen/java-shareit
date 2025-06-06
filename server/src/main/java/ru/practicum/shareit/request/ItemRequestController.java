package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.Collection;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Slf4j
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @Valid @RequestBody ItemRequestCreateDto itemRequestCreateDto) {
        log.info("Добавлен новый запрос вещи от пользователя с ID {}", userId);
        return itemRequestService.create(userId, itemRequestCreateDto);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Long requestId) {
        log.info("Получены данные об одном конкретном запросе c ID {}",requestId);
        return itemRequestService.getAllRequestById(requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests() {
        log.info("Получен список запросов, созданных другими пользователями.");
        return itemRequestService.getAllRequests();
    }

    @GetMapping
    public List<ItemRequestDto> getUserRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен список своих запросов вместе с данными об ответах на них.");
        return itemRequestService.getUserRequests(userId);
    }
}
