package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  Сущность, представляющая запрос вещи в системе.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class ItemRequest {
    /**
     * Уникальный идентификатор запроса. Генерируется автоматически базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Описание необходимой вещи. Обязательное поле.
     */
    @Column(name = "description", nullable = false)
    String description;

    /**
     * Пользователь, создавший запрос.  Связь Many-to-One с сущностью User.
     * Обязательное поле.
     */
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    User requester;

    /**
     *  Дата и время создания запроса. Автоматически устанавливается при создании записи.
     */
    @CreationTimestamp
    @Column(name = "created")
    LocalDateTime created;

    /**
     *  Список вещей, связанных с этим запросом.  Связь One-to-Many с сущностью Item.
     *  `mappedBy` указывает на поле `request` в классе `Item`, которое управляет связью.
     */
    @OneToMany(mappedBy = "request")
    List<Item> items;
}
