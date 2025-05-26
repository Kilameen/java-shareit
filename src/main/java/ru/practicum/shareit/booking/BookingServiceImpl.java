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
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Вещь не найдена."));

        if (!item.getAvailable()) {
            throw new IllegalStateException("Вещь недоступна для бронирования.");
        }
        if (item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Владелец не может бронировать свою вещь.");
        }

        if (bookingDto.getStart().isAfter(bookingDto.getEnd())) {
            throw new IllegalArgumentException("Время начала не может быть позже времени окончания.");
        }

        Booking booking = BookingMapper.toBooking(user, item, bookingDto);
        booking.setStatus(Status.WAITING);
        Booking bookingCreate = bookingRepository.save(booking);
        return BookingMapper.toBookingDto(bookingCreate);
    }


    @Transactional
    @Override
    public BookingDto update(Long bookingId, Boolean approved, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование " + bookingId + " не найдено."));

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

    @Transactional
    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование " + bookingId + " не найдено."));

        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotFoundException("Просматривать бронирование может только владелец вещи или создатель брони.");
        }

        return BookingMapper.toBookingDto(booking);
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
}