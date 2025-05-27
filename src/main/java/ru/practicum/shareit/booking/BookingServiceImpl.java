package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public BookingDto create(Long userId, BookingDto bookingDto) {
        User user = UserMapper.toUser(userService.findUserById(userId));
        // Получаем item из базы данных по ID, указанному в bookingDto
        Optional<Item> itemById = itemRepository.findById(bookingDto.getItemId());
        if (itemById.isEmpty()) {
            throw new NotFoundException("Вещь не найдена.");
        }
        Item item = itemById.get();

        if (item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Владелец не может бронировать свою вещь.");
        }
        // Валидация должна происходить до создания booking, чтобы избежать неконсистентности данных
        BookingValidation(bookingDto,user,item);
        Booking booking = BookingMapper.toBooking(user, item, bookingDto);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }


    @Transactional
    @Override
    public BookingDto update(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование " + bookingId + " не найдено."));

        // Добавляем проверку на null перед использованием booking.getItem()
        if (booking.getItem() == null) {
            throw new IllegalStateException("Item is null for booking " + bookingId);
        }

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotFoundException("Только владелец вещи может подтвердить или отклонить бронирование.");
        }

        if (booking.getStatus() != Status.WAITING) {
            throw new IllegalStateException("Статус можно изменить только у бронирования со статусом WAITING.");
        }

        Status newStatus = approved ? Status.APPROVED : Status.REJECTED;
        booking.setStatus(newStatus);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBookingById(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование " + bookingId + " не найдено."));

        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotFoundException("Просматривать бронирование может только владелец вещи или создатель брони.");
        }

        try {
            return BookingMapper.toBookingDto(booking);
        } catch (Exception e) {
            log.error("Ошибка при маппинге Booking в BookingDto", e);
            throw new RuntimeException("Ошибка при обработке бронирования.", e);
        }
    }

    @Transactional
    @Override
    public List<BookingDto> findAll(Long bookerId, String state) {
        userService.findUserById(bookerId);
        State bookingState = parseState(state);

        List<Booking> bookings;

        LocalDateTime now = LocalDateTime.now();

        bookings = switch (bookingState) {
            case ALL -> bookingRepository.findAllBookingByBookerId(bookerId);
            case CURRENT -> bookingRepository.findCurrentBookingByBookerId(bookerId, now);
            case PAST -> bookingRepository.findPastBookingByBookerId(bookerId, now);
            case FUTURE -> bookingRepository.findFutureBookingByBookerId(bookerId, now);
            case WAITING -> bookingRepository.findWaitingBookingByBookerId(bookerId, now);
            case REJECTED -> bookingRepository.findRejectBookingByBookerId(bookerId, now);
            default -> throw new NotFoundException("Неизвестный state: " + state);
        };

        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<BookingDto> getOwnerBookings(Long ownerId, String state) {
        userService.findUserById(ownerId);
        State bookingState = parseState(state);
        List<Booking> bookings;
        LocalDateTime now = LocalDateTime.now();

        bookings = switch (bookingState) {
            case ALL -> bookingRepository.findAllBookingsByOwnerId(ownerId);
            case CURRENT -> bookingRepository.findAllCurrentBookingsByOwnerId(ownerId, now);
            case PAST -> bookingRepository.findAllPastBookingsByOwnerId(ownerId, now);
            case FUTURE -> bookingRepository.findAllFutureBookingsByOwnerId(ownerId, now);
            case WAITING -> bookingRepository.findAllWaitingBookingsByOwnerId(ownerId, now);
            case REJECTED -> bookingRepository.findAllRejectedBookingsByOwnerId(ownerId);
            default -> throw new NotFoundException("Неизвестный state: " + state);
        };

        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    private State parseState(String stateString) {
        try {
            return State.valueOf(stateString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Неизвестный state: " + stateString);
        }
    }
    private void BookingValidation(BookingDto bookingDto,User user, Item item){
        if (bookingDto.getItemId() == null) { // Проверяем, что itemId не null
            throw new IllegalArgumentException("Item ID не может быть null.");
        }

        if (item == null) { // Проверяем, что item не null
            throw new NotFoundException("Вещь не найдена.");
        }

        if (!item.getAvailable()) {
            throw new IllegalStateException("Вещь недоступна для бронирования.");
        }

        if (bookingDto.getStart().isAfter(bookingDto.getEnd())) {
            throw new IllegalArgumentException("Время начала не может быть позже времени окончания.");
        }
    }
// В методе update необходимо убедиться, что Item не null
}