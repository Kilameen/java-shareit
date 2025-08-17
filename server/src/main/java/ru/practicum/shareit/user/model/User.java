package ru.practicum.shareit.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

/**
 *  Сущность, представляющая пользователя в системе.
 *  Соответствует таблице "users" в базе данных.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    /**
     * Уникальный идентификатор пользователя.
     * Автоматически генерируется базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Имя пользователя.
     * Не может быть null.
     */
    @Column(name = "name", nullable = false)
    String name;

    /**
     * Адрес электронной почты пользователя.
     * Должен быть уникальным и не может быть null.
     */
    @Column(name = "email", nullable = false, unique = true)
    String email;
}
