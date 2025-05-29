package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwner(User owner);


    @Query("SELECT i FROM Item i WHERE LOWER(i.name) LIKE %:text% OR LOWER(i.description) LIKE %:text%")
    List<Item> searchItems(String text);
}

