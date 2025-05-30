package ru.practicum.shareit.item.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.CommentMapper;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Override
    public NewItemDto create(Long userId, ItemDto itemDto) {
        UserDto owner = userService.findUserById(userId);
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner((UserMapper.toUser(owner)));
        return ItemMapper.toNewItemDto(itemRepository.save(item));
    }

    @Override
    public NewItemDto update(Long userId, Long itemId, ItemDto itemDto) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с ID " + itemId + " не найден."));

        if (!Objects.equals(existingItem.getOwner().getId(), userId)) {
            throw new NotFoundException("У пользователя нет прав на обновление предмета с ID " + itemId);
        }

        if (itemDto.getName() != null) {
            existingItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            existingItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            existingItem.setAvailable(itemDto.getAvailable());
        }

        Item updatedItem = itemRepository.save(existingItem);
        log.info("Обновлен предмет с ID: {}", itemId);
        return ItemMapper.toNewItemDto(updatedItem);
    }

    @Override
    @Transactional(readOnly = true)
    public NewItemDto getItemDtoById(Long userId, Long itemId) {
        userService.findUserById(userId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена."));

        NewItemDto newItemDto = ItemMapper.toNewItemDto(item);
        newItemDto.setComments(getItemComments(itemId));

        if (item.getOwner().getId().equals(userId)) {
            List<Booking> bookings = bookingRepository.findAllByItemAndStatusOrderByStartAsc(item, Status.APPROVED);
            List<NewBookingDto> bookingDTOList = bookings
                    .stream()
                    .map(BookingMapper::toNewBookingDto)
                    .collect(toList());

            newItemDto.setLastBooking(getLastBooking(bookingDTOList, LocalDateTime.now()));
            newItemDto.setNextBooking(getNextBooking(bookingDTOList, LocalDateTime.now()));
        }

        return newItemDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<NewItemDto> getAllItemDtoByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));

        List<Item> items = itemRepository.findByOwner(user);

        Map<Long, List<Booking>> bookingsByItemId = bookingRepository.findAllByItemInAndStatusOrderByStartAsc(items, Status.APPROVED)
                .stream()
                .collect(Collectors.groupingBy(booking -> booking.getItem().getId()));

        return items.stream()
                .map(item -> {
                    NewItemDto newItemDto = ItemMapper.toNewItemDto(item);
                    newItemDto.setComments(getItemComments(item.getId()));
                    return newItemDto;
                })
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewItemDto> searchItems(Long userId, String text) {
        userService.findUserById(userId);
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        List<Item> items = itemRepository.searchItems(text);
        log.info("Поиск предметов по тексту: {}. Найдено {} предметов.", text, items.size());
        return items.stream()
                .map(ItemMapper::toNewItemDto)
                .collect(toList());
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        log.info("Удален предмет с ID: {}", itemId);
    }

    @Override
    public NewCommentDto createComment(Long userId, CommentDto commentDto, Long itemId) {
        User user = UserMapper.toUser(userService.findUserById(userId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена."));

        if (bookingRepository.findAllByUserBookings(userId, itemId, LocalDateTime.now()).isEmpty()) {
            throw new ValidationException("У пользователя с id " + userId + " должно быть хотя бы одно завершенное бронирование предмета с id " + itemId + " для возможности оставить комментарий.");
        }

        return CommentMapper.toNewCommentDto(commentRepository.save(CommentMapper.toComment(commentDto, item, user)));

    }

    @Override
    public List<NewCommentDto> getItemComments(Long itemId) {
        return commentRepository.findAllByItemId(itemId)
                .stream()
                .map(CommentMapper::toNewCommentDto)
                .collect(toList());
    }

    private NewBookingDto getLastBooking(List<NewBookingDto> bookings, LocalDateTime time) {
        return bookings.stream()
                .filter(booking -> !booking.getStart().isAfter(time))
                .max(Comparator.comparing(NewBookingDto::getStart))
                .orElse(null);
    }

    private NewBookingDto getNextBooking(List<NewBookingDto> bookings, LocalDateTime time) {
        return bookings.stream()
                .filter(booking -> booking.getStart().isAfter(time))
                .min(Comparator.comparing(NewBookingDto::getStart))
                .orElse(null);
    }
}
