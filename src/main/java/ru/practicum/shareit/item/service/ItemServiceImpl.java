package ru.practicum.shareit.item.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.CommentMapper;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public ItemDto create(Long userId, ItemDto itemDto) {
        UserDto owner = userService.findUserById(userId);
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner((UserMapper.toUser(owner)));
        Item createdItem = itemRepository.save(item);
        log.info("Создан предмет с ID: {}", createdItem.getId());
        return ItemMapper.toItemDto(createdItem);
    }

    @Transactional
    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto) {
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
        return ItemMapper.toItemDto(updatedItem);
    }
    @Override
    @Transactional
    public ItemDto getItemDtoById(Long userId, Long itemId) {
        userService.findUserById(userId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена."));

        ItemDto itemDto = ItemMapper.toItemDto(item);
        itemDto.setComments(getItemComments(itemId));

        if (item.getOwner().getId().equals(userId)) {
            List<Booking> bookings = bookingRepository.findAllByItemAndStatusOrderByStartAsc(item, Status.APPROVED);
            List<BookingDto> bookingDTOList = bookings.stream()
                    .map(BookingMapper::toBookingDto)
                    .collect(toList());

            itemDto.setLastBooking(getLastBooking(bookingDTOList, LocalDateTime.now()));
            itemDto.setNextBooking(getNextBooking(bookingDTOList, LocalDateTime.now()));
        }

        return itemDto;
    }

    @Transactional
    @Override
    public Collection<ItemDto> getAllItemDtoByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));

        List<Item> items = itemRepository.findByOwner(user);
        // Добавляем проверку на пустой список items, чтобы избежать дальнейшей обработки,
        // если у пользователя нет предметов
        if (items.isEmpty()) {
            return Collections.emptyList(); // Возвращаем пустой список, если нет предметов
        }

        List<Booking> bookings = bookingRepository.findAllByItemInAndStatusOrderByStartAsc(items, Status.APPROVED);

        Map<Long, List<Booking>> bookingsByItemId = bookings.stream()
                .collect(Collectors.groupingBy(booking -> booking.getItem().getId()));

        return items.stream()
                .map(item -> {
                    ItemDto itemDto = ItemMapper.toItemDto(item);
                    List<CommentDto> comments = getItemComments(item.getId());
                    itemDto.setComments(comments);
                    return itemDto;
                })
                .collect(toList());
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        List<Item> items = itemRepository.searchItems(text);
        log.info("Поиск предметов по тексту: {}. Найдено {} предметов.", text, items.size()); // Добавлена отладочная информация
        List<ItemDto> itemDtos = items.stream()
                .map(ItemMapper::toItemDto)
                .collect(toList());
        log.info("Преобразовано в {} ItemDto.", itemDtos.size()); // Добавлена отладочная информация
        return itemDtos;
    }


    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        log.info("Удален предмет с ID: {}", itemId);
    }

    @Override
    @Transactional
    public CommentDto createComment(Long userId, CommentDto commentDto, Long itemId){
        User user = UserMapper.toUser(userService.findUserById(userId));
        Optional<Item> itemById = itemRepository.findById(itemId);

        // Проверяем, существует ли вещь
        if(itemById.isEmpty()){
            throw new NotFoundException("Вещь с id = " + itemId + " не найдена.");
        }
        Item item = itemById.get(); // Теперь безопасно вызывать get(), так как проверка пройдена

        //Получаем все завершенные бронирования для пользователя и вещи
        List<Booking> userBookings = bookingRepository.findAllByUserBookings(userId, itemId, LocalDateTime.now());

        // Проверяем, есть ли у пользователя завершенные бронирования для данного предмета
        if (userBookings.isEmpty()) {
            throw new ValidationException("У пользователя с id " + userId + " должно быть хотя бы одно завершенное бронирование предмета с id " + itemId + " для возможности оставить комментарий.");
        }

        return CommentMapper.toCommentDto(commentRepository.save(CommentMapper.toComment(commentDto, item, user)));

    }


    public List<CommentDto>getItemComments(Long itemId){
        List<Comment>comments = commentRepository.findAllByItemId(itemId);
        return comments.stream().map(CommentMapper::toCommentDto).collect(toList());
    }
    private BookingDto getLastBooking(List<BookingDto> bookings, LocalDateTime time) {
        if (bookings == null || bookings.isEmpty()) {
            return null;
        }

        return bookings
                .stream()
                .filter(bookingDTO -> !bookingDTO.getStart().isAfter(time))
                .reduce((booking1, booking2) -> booking1.getStart().isAfter(booking2.getStart()) ? booking1 : booking2)
                .orElse(null);
    }

    private BookingDto getNextBooking(List<BookingDto> bookings, LocalDateTime time) {
        if (bookings == null || bookings.isEmpty()) {
            return null;
        }

        return bookings
                .stream()
                .filter(bookingDTO -> bookingDTO.getStart().isAfter(time))
                .findFirst()
                .orElse(null);
    }
    }
