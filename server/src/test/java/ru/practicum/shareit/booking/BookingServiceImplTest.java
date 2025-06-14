package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private BookingServiceImpl bookingService;

    private User booker;
    private Item item;
    private Booking booking;
    private BookingCreateDto bookingCreateDto;

    @BeforeEach
    void setUp() {
        booker = User.builder().id(1L).name("Booker").email("booker@yandex.ru").build();
        item = Item.builder().id(1L).name("Item").description("Description").available(true).owner(booker).build();
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        bookingCreateDto = BookingCreateDto.builder().itemId(1L).start(start).end(end).build();
        booking = Booking.builder().id(1L).start(start).end(end).item(item).booker(booker).status(Status.WAITING).build();
    }

    @Test
    void createBookingReturnBookingDto() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingDto result = bookingService.create(1L, bookingCreateDto);

        assertNotNull(result);
        assertEquals(booking.getId(), result.getId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void createBookingWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookingService.create(1L, bookingCreateDto);
        });

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void updateBookingReturnUpdatedBookingDto() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        BookingDto result = bookingService.update(1L, 1L, true);

        assertNotNull(result);
        assertEquals(Status.APPROVED, result.getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void getBookingByIdReturnBookingDto() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        BookingDto result = bookingService.getBookingById(1L, 1L);

        assertNotNull(result);
        assertEquals(booking.getId(), result.getId());
    }

    @Test
    void findAllReturnListOfBookingDto() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(bookingRepository.findByBookerIdOrderByStartDesc(anyLong())).thenReturn(List.of(booking));
        List<BookingDto> result = bookingService.findAll(1L, "ALL", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getOwnerBookingsReturnListOfBookingDto() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(List.of(booking));
        List<BookingDto> result = bookingService.getOwnerBookings(1L, "ALL", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void updateBookingWhenBookingNotFound() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookingService.update(1L, 1L, true);
        });

        assertEquals("Бронирование 1 не найдено.", exception.getMessage());
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void updateBookingWhenStatusIsAlreadyApproved() {
        booking.setStatus(Status.APPROVED);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            bookingService.update(1L, 1L, true);
        });

        assertEquals("Статус можно изменить только у бронирования со статусом WAITING.", exception.getMessage());
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void createBookingWhenItemIsNotAvailable() {
        item.setAvailable(false);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            bookingService.create(1L, bookingCreateDto);
        });

        assertEquals("Вещь не доступна для бронирования.", exception.getMessage());
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void getBookingById_BookingNotFound_ThrowsNotFoundException() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.getBookingById(1L, 1L));
    }

    @Test
    void updateBooking_BookingNotFound_ThrowsNotFoundException() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.update(1L, 1L, true));
    }
}