package ru.practicum.shareit.booking;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
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
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Override
    public NewBookingDto create(Long userId, BookingDto bookingDto) {
        User booker = UserMapper.toUser(userService.findUserById(userId));
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Вещь не найдена."));

        bookingValidation(bookingDto, item);
        Booking booking = BookingMapper.toBooking(bookingDto, item, booker);

        return BookingMapper.toNewBookingDto(bookingRepository.save(booking));
    }

    @Override
    public NewBookingDto update(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование " + bookingId + " не найдено."));

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new ValidationException("Только владелец вещи может подтвердить или отклонить бронирование.");
        }

        if (booking.getStatus() != Status.WAITING) {
            throw new IllegalStateException("Статус можно изменить только у бронирования со статусом WAITING.");
        }

        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        return BookingMapper.toNewBookingDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public NewBookingDto getBookingById(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование " + bookingId + " не найдено."));

        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotFoundException("Просматривать бронирование может только владелец вещи или создатель брони.");
        }
        return BookingMapper.toNewBookingDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NewBookingDto> findAll(Long bookerId, String state) {
        userService.findUserById(bookerId);
        State bookingState = parseState(state);
        LocalDateTime now = LocalDateTime.now();

        List<Booking> bookings = switch (bookingState) {
            case ALL -> bookingRepository.findAllBookingByBookerId(bookerId);
            case CURRENT -> bookingRepository.findCurrentBookingByBookerId(bookerId, now);
            case PAST -> bookingRepository.findPastBookingByBookerId(bookerId, now);
            case FUTURE -> bookingRepository.findFutureBookingByBookerId(bookerId, now);
            case WAITING -> bookingRepository.findWaitingBookingByBookerId(bookerId, now);
            case REJECTED -> bookingRepository.findRejectBookingByBookerId(bookerId, now);
        };

        return bookings.stream()
                .map(BookingMapper::toNewBookingDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<NewBookingDto> getOwnerBookings(Long ownerId, String state) {
        userService.findUserById(ownerId);
        State bookingState = parseState(state);
        LocalDateTime now = LocalDateTime.now();

        List<Booking> bookings = switch (bookingState) {
            case ALL -> bookingRepository.findAllBookingsByOwnerId(ownerId);
            case CURRENT -> bookingRepository.findAllCurrentBookingsByOwnerId(ownerId, now);
            case PAST -> bookingRepository.findAllPastBookingsByOwnerId(ownerId, now);
            case FUTURE -> bookingRepository.findAllFutureBookingsByOwnerId(ownerId, now);
            case WAITING -> bookingRepository.findAllWaitingBookingsByOwnerId(ownerId, now);
            case REJECTED -> bookingRepository.findAllRejectedBookingsByOwnerId(ownerId);
        };

        return bookings.stream()
                .map(BookingMapper::toNewBookingDto)
                .collect(Collectors.toList());
    }

    private State parseState(String stateString) {
        try {
            return State.valueOf(stateString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Неизвестный state: " + stateString);
        }
    }

    private void bookingValidation(BookingDto bookingDto, Item item) {
        if (!item.getAvailable()) {
            throw new IllegalStateException("Вещь недоступна для бронирования.");
        }

        if (!bookingDto.getStart().isBefore(bookingDto.getEnd())) {
            throw new IllegalArgumentException("Время начала должно быть раньше времени окончания.");
        }
    }
}