package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.Collection;
import java.util.List;

public interface ItemService {

    /**
     * Создает новый предмет.
     *
     * @param userId        ID пользователя, создающего предмет.
     * @param itemCreateDto DTO с информацией о создаваемом предмете.
     * @return DTO созданного предмета.
     */
    ItemDto create(Long userId, ItemCreateDto itemCreateDto);

    /**
     * Обновляет существующий предмет.
     *
     * @param userId        ID пользователя, обновляющего предмет.
     * @param itemId        ID обновляемого предмета.
     * @param itemUpdateDto DTO с информацией об обновлении предмета.
     * @return DTO обновленного предмета.
     */
    ItemDto update(Long userId, Long itemId, ItemUpdateDto itemUpdateDto);

    /**
     * Возвращает предмет по его ID.
     *
     * @param userId ID пользователя, запрашивающего предмет (необходим для проверок).
     * @param itemId        ID предмета.
     * @return DTO предмета.
     */
    ItemDto getItemDtoById(Long userId, Long itemId);

    /**
     * Возвращает все предметы, принадлежащие пользователю.
     *
     * @param userId ID пользователя.
     * @return Коллекция DTO предметов.
     */
    Collection<ItemDto> getAllItemDtoByUserId(Long userId);

    /**
     * Поиск предметов по тексту.
     *
     * @param userId ID пользователя, выполняющего поиск.
     * @param text   Текст для поиска.
     * @return Список DTO найденных предметов.
     */
    List<ItemDto> searchItems(Long userId, String text);

    /**
     * Удаляет предмет.
     *
     * @param itemId ID удаляемого предмета.
     */
    void deleteItem(Long itemId);

    /**
     * Создает комментарий к предмету.
     *
     * @param userId          ID пользователя, оставляющего комментарий.
     * @param commentCreateDto DTO с информацией о создаваемом комментарии.
     * @param itemId          ID предмета, к которому добавляется комментарий.
     * @return DTO созданного комментария.
     */
    CommentDto createComment(Long userId, CommentCreateDto commentCreateDto, Long itemId);

    /**
     * Возвращает комментарии к предмету.
     *
     * @param itemId ID предмета.
     * @return Список DTO комментариев.
     */
    List<CommentDto> getItemComments(Long itemId);
}
