package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class UserServiceImpl implements UserService {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        log.info("Попытка создать пользователя с email: {}", user.getEmail());
        validate(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Пользователь с id {} и email {} успешно создан", user.getId(), user.getEmail());
        return user;
    }

    @Override
    public User update(User updateUser) {
        log.info("Попытка обновить пользователя с id: {}", updateUser.getId());
        validate(updateUser);
        if (users.containsKey(updateUser.getId())) {
            User oldInformationUser = users.get(updateUser.getId());
            oldInformationUser.setName(updateUser.getName());
            oldInformationUser.setEmail(updateUser.getEmail());
            log.info("Пользователь с id {} успешно обновлен", updateUser.getId());
        } else {
            log.warn("Пользователь с id {} не найден для обновления", updateUser.getId());
            throw new NotFoundException("Пользователь с id " + updateUser.getId() + " не найден");
        }
        return updateUser;
    }
    @Override
    public Collection<User> findAll(){
        log.info("Список всех пользователей");
        return users.values();
    }

    @Override
    public User findUserById(Long id){
        log.info("Поиск пользователя по ID: {}", id);
        return users.get(id);
    }

    @Override
    public void delete(Long id){
        log.info("Удаление пользователя по ID: {}", id);
        users.remove(id);
    }

    private void validate(User user) throws DuplicatedDataException {
        if (users.values()
                .stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()) && !Objects.equals(u.getId(), user.getId()))) {
            log.error("Email {} уже используется", user.getEmail());
            throw new DuplicatedDataException("Этот email уже используется");
        }

        if (users.values()
                .stream()
                .anyMatch(u -> u.getName().equals(user.getName()) && !Objects.equals(u.getId(), user.getId()))) {
            log.error("Имя {} уже используется", user.getName());
            throw new DuplicatedDataException("Это имя уже используется");
        }
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}