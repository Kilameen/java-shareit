package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с запросами вещей.
 * Определяет основные операции, которые можно выполнять с запросами вещей.
 */
public interface ItemRequestService {

    /**
     * Создает новый запрос вещи.
     * @param userId Идентификатор пользователя, создающего запрос.
     * @param itemRequestCreateDto DTO с информацией о запросе.
     * @return DTO созданного запроса.
     */
    ItemRequestDto create(Long userId, ItemRequestCreateDto itemRequestCreateDto);

    /**
     * Возвращает список запросов, созданных указанным пользователем.
     * @param userId Идентификатор пользователя.
     * @return Список DTO запросов.
     */
    List<ItemRequestDto> getUserRequests(Long userId);

    /**
     * Возвращает список всех запросов, кроме запросов, созданных указанным пользователем,
     * с возможностью пагинации.
     * @param userId Идентификатор пользователя, для которого не нужно возвращать запросы.
     * @param from Индекс первого элемента, начиная с 0.
     * @param size Количество элементов на странице.
     * @return Список DTO запросов.
     */
    List<ItemRequestDto> getAllRequests(Long userId, Integer from, Integer size);

    /**
     * Возвращает запрос вещи по его идентификатору.
     * @param requestId Идентификатор запроса.
     * @return DTO запроса.
     */
    ItemRequestDto getAllRequestById(Long requestId);
}
