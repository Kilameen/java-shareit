package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.UserRequestDto;

/**
 * Клиент для взаимодействия с API пользователей на сервере ShareIt.
 */
@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    /**
     * Конструктор класса.
     * @param serverUrl URL сервера ShareIt.
     * @param builder Строитель RestTemplate.
     */
    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    /**
     * Создает нового пользователя.
     * @param userDto DTO с данными пользователя.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> create(UserRequestDto userDto) {
        return post("", userDto);
    }

    /**
     * Находит пользователя по ID.
     * @param userId ID пользователя.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> findById(Long userId) {
        return get("/" + userId);
    }

    /**
     * Возвращает всех пользователей.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> findAll() {
        return get("/");
    }

    /**
     * Обновляет пользователя.
     * @param userId ID пользователя.
     * @param userDto DTO с данными пользователя для обновления.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> update(Long userId, UserRequestDto userDto) {
        return patch("/" + userId, userDto);
    }

    /**
     * Удаляет пользователя по ID.
     * @param userId ID пользователя.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> deleteById(Long userId) {
        return delete("/" + userId);
    }
}
