package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.Collections;
import java.util.Map;

/**
 *  Клиент для взаимодействия с Item сервисом.  Предоставляет методы для создания, обновления,
 *  получения и поиска вещей, а также для добавления комментариев.
 */
@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    /**
     * Создает новую вещь.
     * @param userId Идентификатор пользователя, создающего вещь.
     * @param itemDto DTO с информацией о создаваемой вещи.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> create(Long userId, ItemCreateDto itemDto) {
        return post("", userId, itemDto);
    }

    /**
     * Обновляет существующую вещь.
     * @param userId Идентификатор пользователя, обновляющего вещь.
     * @param itemId Идентификатор вещи, которую нужно обновить.
     * @param itemDto DTO с информацией для обновления вещи.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> update(Long userId, Long itemId, ItemUpdateDto itemDto) {
        return patch("/" + itemId, userId, itemDto);
    }

    /**
     *  Находит вещь по её идентификатору.
     * @param itemId Идентификатор искомой вещи.
     * @param requesterId Идентификатор пользователя, запрашивающего вещь.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> findItemById(Long itemId, Long requesterId) {
        return get("/" + itemId, requesterId);
    }

    /**
     * Возвращает все вещи пользователя.
     * @param userId Идентификатор пользователя, чьи вещи нужно получить.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> findAll(Long userId) {

        return get("", userId, null);
    }

    /**
     *  Удаляет вещь по её идентификатору.
     * @param itemId Идентификатор вещи, которую нужно удалить.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> deleteById(Long itemId) {
        return delete("/" + itemId);
    }

    /**
     * Ищет вещи по тексту в названии или описании.
     * @param userId Идентификатор пользователя, выполняющего поиск.
     * @param text Текст для поиска.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> searchItems(Long userId, String text) {
        if (text.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        Map<String, Object> parameters = Map.of(

                "text", text
        );

        return get("/search?text={text}", userId, parameters);
    }

    /**
     * Добавляет комментарий к вещи.
     * @param userId Идентификатор пользователя, добавляющего комментарий.
     * @param commentDto DTO с информацией о комментарии.
     * @param itemId Идентификатор вещи, к которой добавляется комментарий.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> createComment(Long userId, CommentRequestDto commentDto, Long itemId) {
        return post("/" + itemId + "/comment", userId, commentDto);
    }
}
