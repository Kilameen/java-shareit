package ru.practicum.shareit.item.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.exception.NotFoundException;

import ru.practicum.shareit.booking.dto.Status;

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
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public ItemDto create(Long userId, ItemCreateDto itemCreateDto) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        Item item = ItemMapper.toItemFromCreateDto(itemCreateDto);
        item.setOwner(owner);
        if (itemCreateDto.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestRepository.findById(itemCreateDto.getRequestId())
                    .orElseThrow(() -> new EntityNotFoundException("Запроса на данный предмет не существует"));
            item.setRequest(itemRequest);
        }
        return ItemMapper.toItemFromDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemUpdateDto itemUpdateDto) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с ID " + itemId + " не найден."));

        if (!Objects.equals(existingItem.getOwner().getId(), userId)) {
            throw new NotFoundException("У пользователя нет прав на обновление предмета с ID " + itemId);
        }
        if (itemUpdateDto.getName() != null) {
            existingItem.setName(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null) {
            existingItem.setDescription(itemUpdateDto.getDescription());
        }
        if (itemUpdateDto.getAvailable() != null) {
            existingItem.setAvailable(itemUpdateDto.getAvailable());
        }
        itemRepository.save(existingItem);
        log.info("Обновлен предмет с ID: {}", itemId);
        return ItemMapper.toItemFromDto(existingItem);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItemDtoById(Long itemId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена."));

        ItemDto newItemDto = ItemMapper.toItemFromDto(item);
        List<CommentDto> comments = getItemComments(itemId);
        newItemDto.setComments(comments);

        if (item.getOwner().getId().equals(userId)) {
            List<Booking> bookings = bookingRepository.findAllByItemAndStatusOrderByStartAsc(item, Status.APPROVED);
            List<BookingDto> bookingDTOList = bookings
                    .stream()
                    .map(BookingMapper::toBookingDto)
                    .collect(toList());

            newItemDto.setLastBooking(getLastBooking(bookingDTOList, LocalDateTime.now()));
            newItemDto.setNextBooking(getNextBooking(bookingDTOList, LocalDateTime.now()));
        }

        return newItemDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<ItemDto> getAllItemDtoByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));

        List<Item> items = itemRepository.findByOwner(user);

        Map<Long, List<Booking>> bookingsByItemId = bookingRepository.findAllByItemInAndStatusOrderByStartAsc(items, Status.APPROVED)
                .stream()
                .collect(Collectors.groupingBy(booking -> booking.getItem().getId()));

        return items.stream()
                .map(item -> {
                    ItemDto newItemDto = ItemMapper.toItemFromDto(item);
                    newItemDto.setComments(getItemComments(item.getId()));
                    return newItemDto;
                })
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> searchItems(Long userId, String text) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        List<Item> items = itemRepository.searchItems(text);
        log.info("Поиск предметов по тексту: {}. Найдено {} предметов.", text, items.size());
        return items.stream()
                .map(ItemMapper::toItemFromDto)
                .collect(toList());
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        log.info("Удален предмет с ID: {}", itemId);
    }

    @Override
    public CommentDto createComment(Long userId, CommentCreateDto commentCreateDto, Long itemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена."));

        if (bookingRepository.findAllByUserBookings(userId, itemId, LocalDateTime.now()).isEmpty()) {
            throw new ValidationException("У пользователя с id " + userId + " должно быть хотя бы одно завершенное бронирование предмета с id " + itemId + " для возможности оставить комментарий.");
        }

        Comment comment = CommentMapper.toComment(commentCreateDto, item, user);
        Comment savedComment = commentRepository.save(comment);
        log.info("Создан комментарий с ID: {} для item ID: {}", savedComment.getId(), itemId);

        return CommentMapper.toCommentDto(savedComment);
    }

    @Override
    public List<CommentDto> getItemComments(Long itemId) {
        List<Comment> comments = commentRepository.findAllByItemId(itemId);
        log.info("Найдено {} комментариев для item ID: {}", comments.size(), itemId);
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(toList());
    }

    private BookingDto getLastBooking(List<BookingDto> bookings, LocalDateTime time) {
        return bookings.stream()
                .filter(booking -> !booking.getStart().isAfter(time))
                .max(Comparator.comparing(BookingDto::getStart))
                .orElse(null);
    }

    private BookingDto getNextBooking(List<BookingDto> bookings, LocalDateTime time) {
        return bookings.stream()
                .filter(booking -> booking.getStart().isAfter(time))
                .min(Comparator.comparing(BookingDto::getStart))
                .orElse(null);
    }
}