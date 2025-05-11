package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserDtoService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserDtoService userDtoService;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userDtoService.create(userDto);
    }

    @PatchMapping("/{id}")
    public UserDto update(@Valid @PathVariable Long id, @RequestBody UserDto userDto) {
        return userDtoService.update(id, userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userDtoService.findUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDtoService.delete(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userDtoService.findAll();
    }
}
