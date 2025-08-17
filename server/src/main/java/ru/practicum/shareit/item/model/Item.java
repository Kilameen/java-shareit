package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 *  Сущность, представляющая собой предмет (вещь), доступную для шеринга.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {
    /**
     * Уникальный идентификатор предмета. Автоматически генерируется базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Название предмета. Обязательное поле.
     */
    @Column(name = "name", nullable = false)
    String name;

    /**
     * Описание предмета. Обязательное поле.
     */
    @Column(name = "description", nullable = false)
    String description;

    /**
     * Доступность предмета для шеринга. Обязательное поле.
     */
    @Column(name = "is_available", nullable = false)
    Boolean available;

    /**
     * Владелец предмета. Связь Many-to-One с сущностью User.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    User owner;

    /**
     * Запрос на предмет, если он был создан по запросу. Связь Many-to-One с сущностью ItemRequest. Может быть null.
     */
    @ManyToOne
    @JoinColumn(name = "request_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    ItemRequest request;
}
