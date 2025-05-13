package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        User createdUser = userRepository.create(user);
        return UserMapper.toUserDto(createdUser);
    }

    @Override
    public UserDto update(Long id, UserDto userUpdateDto) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }

        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }

        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().equals(user.getEmail())) {
            validateEmail(userUpdateDto.getEmail(), id);
            user.setEmail(userUpdateDto.getEmail());
        }

        User updatedUser = userRepository.update(user);
        return UserMapper.toUserDto(updatedUser);
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    private void validateEmail(String email, Long userId) {
        userRepository.findAll().forEach(user -> {
            if (user.getEmail().equals(email) && !user.getId().equals(userId)) {
                throw new DuplicatedDataException("Email уже используется");
            }
        });
    }
}