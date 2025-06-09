package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@Table(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "description",nullable = false)
    String description;
    @ManyToOne
    @JoinColumn(name = "requester_id",nullable = false)
    User requester;
    @CreationTimestamp
    @Column(name = "created",nullable = false)
    LocalDateTime created;
    @OneToMany(mappedBy = "request")
    @JoinColumn(name = "request_id")
    List<Item> items = new ArrayList<>();
}
