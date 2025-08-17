package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.Constants;

import java.util.Collection;
import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с вещами (Item).
 */
@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * Обрабатывает POST-запрос на создание новой вещи.
     * @param userId Идентификатор пользователя, который создает вещь. Берется из заголовка запроса.
     * @param itemCreateDto DTO, содержащий данные для создания вещи.
     * @return DTO созданной вещи.
     */
    @PostMapping
    public ItemDto create(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                          @RequestBody ItemCreateDto itemCreateDto) {
        log.info("POST запрос на создание новой вещи: {} от пользователя c id: {}", itemCreateDto, userId);
        return itemService.create(userId, itemCreateDto);
    }

    /**
     * Обрабатывает PATCH-запрос на обновление информации о вещи.
     * @param userId Идентификатор пользователя, выполняющего обновление. Берется из заголовка запроса.
     * @param itemId Идентификатор вещи, которую необходимо обновить.
     * @param itemUpdateDto DTO, содержащий данные для обновления вещи.
     * @return DTO обновленной вещи.
     */
    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                          @PathVariable Long itemId, @RequestBody ItemUpdateDto itemUpdateDto) {
        log.info("PATCH запрос на обновление вещи id: {} пользователя c id: {}", itemId, userId);
        return itemService.update(userId, itemId, itemUpdateDto);
    }

    /**
     * Обрабатывает GET-запрос на получение информации о вещи по её идентификатору.
     * @param userId Идентификатор пользователя, запрашивающего информацию.
     * @param itemId Идентификатор вещи.
     * @return DTO вещи.
     */
    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                               @PathVariable("itemId") Long itemId) {
        log.info("GET запрос на получение вещи");
        return itemService.getItemDtoById(itemId, userId);
    }

    /**
     * Обрабатывает GET-запрос на получение всех вещей, принадлежащих определенному пользователю.
     * @param userId Идентификатор пользователя.
     * @return Коллекция DTO вещей.
     */
    @GetMapping
    public Collection<ItemDto> getItemsByUserId(@RequestHeader(Constants.USER_ID_HEADER) Long userId) {
        log.info("GET запрос на получение всех вещей пользователя c id: {}", userId);
        return itemService.getAllItemDtoByUserId(userId);
    }

    /**
     * Обрабатывает GET-запрос на поиск вещей по текстовому запросу.
     * @param userId Идентификатор пользователя, выполняющего поиск.
     * @param text Текст для поиска в названии или описании вещей.
     * @return Список DTO найденных вещей.
     */
    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(Constants.USER_ID_HEADER) Long userId, @RequestParam String text) {
        log.info("GET запрос на поиск всех вещей c текстом: {}", text);
        return itemService.searchItems(userId, text);
    }

    /**
     * Обрабатывает DELETE-запрос на удаление вещи.
     * @param itemId Идентификатор вещи, которую необходимо удалить.
     */
    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        log.info("DELETE запрос на удаление вещи с id: {}", itemId);
        itemService.deleteItem(itemId);
    }

    /**
     * Обрабатывает POST-запрос на создание комментария к вещи.
     * @param userId Идентификатор пользователя, оставляющего комментарий.
     * @param commentCreateDto DTO, содержащий текст комментария.
     * @param itemId Идентификатор вещи, к которой оставляется комментарий.
     * @return DTO созданного комментария.
     */
    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                    @RequestBody CommentCreateDto commentCreateDto,
                                    @PathVariable Long itemId) {
        log.info("POST запрос на создание нового комментария: от пользователя c id: {}", userId);
        return itemService.createComment(userId, commentCreateDto, itemId);
    }
}
