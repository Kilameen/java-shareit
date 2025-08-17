package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.utils.Constants;

/**
 *  Контроллер для обработки запросов на поиск вещей (ItemRequest).
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {

    private final RequestClient requestClient;

    /**
     * Обрабатывает POST-запрос на создание запроса вещи.
     * @param userId Идентификатор пользователя, отправляющего запрос (извлекается из заголовка).
     * @param requestDto DTO с данными запроса.
     * @return ResponseEntity с информацией о созданном запросе.
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                         @Valid @RequestBody ItemRequestCreateDto requestDto) {
        log.info("POST запрос вещи от пользователя с ID {}", userId);
        return requestClient.create(userId, requestDto);
    }

    /**
     * Обрабатывает GET-запрос на получение списка запросов пользователя.
     * @param userId Идентификатор пользователя.
     * @return ResponseEntity со списком запросов пользователя и данными об ответах на них.
     */
    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader(Constants.USER_ID_HEADER) Long userId) {
        log.info("GET запрос на получение списка своих запросов вместе с данными об ответах на них.");
        return requestClient.findUserRequests(userId);
    }

    /**
     * Обрабатывает GET-запрос на получение всех запросов, созданных другими пользователями.
     * @param userId Идентификатор пользователя, выполняющего запрос.
     * @param from Индекс первого элемента для постраничного вывода.
     * @param size Количество элементов на странице.
     * @return ResponseEntity со списком всех запросов (постранично).
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                                 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("GET запрос на получение списка запросов, созданных другими пользователями.");
        return requestClient.findAllRequests(userId, from, size);
    }

    /**
     * Обрабатывает GET-запрос на получение запроса по ID.
     * @param requestId Идентификатор запроса.
     * @return ResponseEntity с данными запроса.
     */
    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findRequestById(@PathVariable Long requestId) {
        log.info("GET запрос на получение данные об одном конкретном запросе c ID {}", requestId);
        return requestClient.findRequestById(requestId);
    }
}
