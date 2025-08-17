package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.utils.Constants;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    /**
     * Endpoint для создания запроса вещи.
     * @param userId ID пользователя, отправляющего запрос, берется из заголовка запроса.
     * @param itemRequestCreateDto DTO, содержащий данные для создания запроса.
     * @return DTO созданного запроса.
     */
    @PostMapping
    public ItemRequestDto create(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                 @RequestBody ItemRequestCreateDto itemRequestCreateDto) {
        log.info("POST запрос вещи от пользователя с ID {}", userId);
        return itemRequestService.create(userId, itemRequestCreateDto);
    }

    /**
     * Endpoint для получения запроса вещи по ID.
     * @param requestId ID запроса.
     * @return DTO запроса.
     */
    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Long requestId) {
        log.info("GET запрос на получение данные об одном конкретном запросе c ID {}", requestId);
        return itemRequestService.getAllRequestById(requestId);
    }

    /**
     * Endpoint для получения всех запросов, созданных другими пользователями.
     * Реализована пагинация.
     * @param userId ID пользователя, запросившего список. Его запросы исключаются из результата.
     * @param from Индекс первого элемента.
     * @param size Количество элементов на странице.
     * @return Список DTO запросов.
     */
    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET запрос на получение списка запросов, созданных другими пользователями.");
        return itemRequestService.getAllRequests(userId, from, size);
    }

    /**
     * Endpoint для получения списка запросов, созданных текущим пользователем.
     * @param userId ID пользователя, запросившего список, берется из заголовка запроса.
     * @return Список DTO запросов.
     */
    @GetMapping
    public List<ItemRequestDto> getUserRequests(@RequestHeader(Constants.USER_ID_HEADER) Long userId) {
        log.info("GET запрос на получение списка своих запросов вместе с данными об ответах на них.");
        return itemRequestService.getUserRequests(userId);
    }
}
