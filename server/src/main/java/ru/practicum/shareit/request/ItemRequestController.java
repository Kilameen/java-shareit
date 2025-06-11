package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @RequestBody ItemRequestCreateDto itemRequestCreateDto) {
        log.info("Добавлен новый запрос вещи от пользователя с ID {}", userId);
        return itemRequestService.create(userId, itemRequestCreateDto);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Long requestId){
        log.info("Получены данные об одном конкретном запросе c ID {}",requestId);
        return itemRequestService.getAllRequestById(requestId);
    }

    @GetMapping("/all") // Endpoint для получения всех запросов (с пагинацией)
    public List<ItemRequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получен список запросов, созданных другими пользователями."); // Логируем действие
        return itemRequestService.getAllRequests(userId, from, size); // Вызываем сервис для получения всех запросов
    }

    @GetMapping
    public List<ItemRequestDto> getUserRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен список своих запросов вместе с данными об ответах на них.");
        return itemRequestService.getUserRequests(userId);
    }
}
