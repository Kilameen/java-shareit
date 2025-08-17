package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая комментарий к вещи.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comments")
public class Comment {
    /**
     * Уникальный идентификатор комментария.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    /**
     * Текст комментария. Не может быть null.
     */
    @Column(name = "text", nullable = false)
    String text;

    /**
     * Вещь, к которой относится комментарий.
     * Связь Many-to-One: много комментариев к одной вещи.
     */
    @ManyToOne
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    Item item;

    /**
     * Автор комментария.
     * Связь Many-to-One: много комментариев у одного пользователя.
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    User author;

    /**
     * Дата и время создания комментария.
     * Автоматически устанавливается при создании комментария.
     */
    @Column(name = "created")
    @CreationTimestamp
    LocalDateTime created;
}
