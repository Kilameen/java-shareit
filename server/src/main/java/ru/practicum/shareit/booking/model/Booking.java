package ru.practicum.shareit.booking.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity класс, представляющий бронирование вещи.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
public class Booking {
    /**
     * Уникальный идентификатор бронирования.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    /**
     * Дата и время начала бронирования.
     */
    @Column(name = "start_date", nullable = false)
    LocalDateTime start;
    /**
     * Дата и время окончания бронирования.
     */
    @Column(name = "end_date", nullable = false)
    LocalDateTime end;
    /**
     * Вещь, которую забронировали.  Связь Many-to-One с таблицей вещей.
     */
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @ToString.Exclude
    Item item;
    /**
     * Пользователь, который забронировал вещь. Связь Many-to-One с таблицей пользователей.
     */
    @ManyToOne
    @JoinColumn(name = "booker_id", nullable = false)
    @ToString.Exclude
    User booker;
    /**
     * Статус бронирования (WAITING, APPROVED, REJECTED).
     */
    @Enumerated(EnumType.STRING)
    Status status;
}
