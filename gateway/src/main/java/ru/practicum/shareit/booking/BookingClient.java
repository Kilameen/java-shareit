package ru.practicum.shareit.booking;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.client.BaseClient;

/**
 * Клиент для взаимодействия с сервисом бронирования.
 * Этот класс отвечает за отправку HTTP-запросов к серверу бронирования.
 */
@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    /**
     * Конструктор класса.
     * @param serverUrl URL сервера бронирования.  Инжектится из application.properties.
     * @param builder  Строитель RestTemplate для создания экземпляра RestTemplate.
     */
    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)) //Устанавливаем базовый URL для всех запросов
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory()) //Используем HttpComponents для поддержки различных HTTP методов.
                        .build()
        );
    }

    /**
     * Создает новое бронирование.
     * @param userId ID пользователя, который создает бронирование.  Передается в Header.
     * @param requestDto DTO с информацией о бронировании. Передается в теле запроса.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> create(Long userId, BookItemRequestDto requestDto) {
        return post("", userId, requestDto);
    }

    /**
     * Обновляет статус бронирования (подтверждает или отклоняет).
     * @param userId ID пользователя, который обновляет бронирование.
     * @param bookingId ID бронирования, которое нужно обновить.
     * @param approved  Статус подтверждения (true - подтверждено, false - отклонено).
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> update(Long userId, Long bookingId, Boolean approved) {
        Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + bookingId + "?approved={approved}", userId, parameters, null);

    }

    /**
     * Получает список бронирований пользователя.
     * @param userId ID пользователя, для которого нужно получить бронирования.
     * @param state  Состояние бронирований (ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED).
     * @param from  Индекс первого элемента в списке (для пагинации).
     * @param size  Количество элементов на странице (для пагинации).
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> getBookings(Long userId, BookingState state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    /**
     * Получает список бронирований вещей, принадлежащих владельцу.
     * @param ownerId ID владельца вещей.
     * @param state  Состояние бронирований.
     * @param from  Индекс первого элемента в списке (для пагинации).
     * @param size  Количество элементов на странице (для пагинации).
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> getAllOwner(Long ownerId, BookingState state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );

        return get("/owner?state={state}&from={from}&size={size}", ownerId, parameters);
    }

    /**
     * Получает информацию о конкретном бронировании.
     * @param userId ID пользователя, который запрашивает информацию.
     * @param bookingId ID бронирования.
     * @return ResponseEntity с результатом запроса.
     */
    public ResponseEntity<Object> getBooking(Long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }
}
