package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.utils.Constants;

/**
 * Контроллер для обработки запросов, связанных с управлением вещами (Items).
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemClient itemClient;

    /**
     * Обрабатывает POST-запрос на создание новой вещи.
     * @param userId Идентификатор пользователя, выполняющего запрос (извлекается из заголовка).
     * @param itemCreateDto DTO с данными для создания вещи.
     * @return ResponseEntity с информацией о созданной вещи.
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                         @Valid @RequestBody ItemCreateDto itemCreateDto) {
        log.info("POST запрос на создание новой вещи: {} от пользователя c id: {}", itemCreateDto, userId);
        return itemClient.create(userId, itemCreateDto);
    }

    /**
     * Обрабатывает PATCH-запрос на обновление существующей вещи.
     * @param userId Идентификатор пользователя, выполняющего запрос.
     * @param itemDto DTO с данными для обновления вещи.
     * @param itemId Идентификатор вещи, которую нужно обновить.
     * @return ResponseEntity с информацией об обновленной вещи.
     */
    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                         @RequestBody ItemUpdateDto itemDto,
                                         @PathVariable("itemId") Long itemId) {
        log.info("PATCH запрос на обновление вещи id: {} пользователя c id: {}", itemId, userId);
        return itemClient.update(userId, itemId, itemDto);
    }

    /**
     * Обрабатывает GET-запрос на получение вещи по её идентификатору.
     * @param requesterId Идентификатор пользователя, выполняющего запрос.
     * @param itemId Идентификатор вещи.
     * @return ResponseEntity с информацией о найденной вещи.
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findItemById(@RequestHeader(Constants.USER_ID_HEADER) Long requesterId,
                                               @PathVariable Long itemId) {
        log.info("GET запрос на получение вещи");
        return itemClient.findItemById(itemId, requesterId);
    }

    /**
     * Обрабатывает GET-запрос на получение всех вещей, принадлежащих пользователю.
     * @param userId Идентификатор пользователя.
     * @return ResponseEntity со списком вещей пользователя.
     */
    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader(Constants.USER_ID_HEADER) Long userId) {
        log.info("GET запрос на получение всех вещей пользователя c id: {}", userId);
        return itemClient.findAll(userId);
    }

    /**
     * Обрабатывает GET-запрос на поиск вещей по тексту в названии или описании.
     * @param userId Идентификатор пользователя.
     * @param text Текст для поиска.
     * @return ResponseEntity со списком найденных вещей.
     */
    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                              @RequestParam String text) {
        log.info("GET запрос на поиск всех вещей c текстом: {}", text);
        return itemClient.searchItems(userId, text);
    }

    /**
     * Обрабатывает DELETE-запрос на удаление вещи по её идентификатору.
     * @param itemId Идентификатор вещи, которую нужно удалить.
     * @return ResponseEntity с подтверждением удаления.
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> delete(@PathVariable Long itemId) {
        log.info("DELETE запрос на удаление вещи с id: {}", itemId);
        return itemClient.deleteById(itemId);
    }

    /**
     * Обрабатывает POST-запрос на добавление комментария к вещи.
     * @param userId Идентификатор пользователя, оставляющего комментарий.
     * @param commentDto DTO с текстом комментария.
     * @param itemId Идентификатор вещи, к которой добавляется комментарий.
     * @return ResponseEntity с информацией о созданном комментарии.
     */
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                                @Valid @RequestBody CommentRequestDto commentDto,
                                                @PathVariable Long itemId) {
        log.info("POST запрос на создание нового комментария: от пользователя c id: {}", userId);
        return itemClient.createComment(userId, commentDto, itemId);
    }
}
