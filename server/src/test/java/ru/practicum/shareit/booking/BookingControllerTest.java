package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    private BookingDto bookingDto;
    private BookingCreateDto bookingCreateDto;

    @BeforeEach
    void setUp() {
        bookingDto = new BookingDto();
        bookingDto.setId(1L);

        bookingCreateDto = new BookingCreateDto();
        bookingCreateDto.setItemId(1L);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void createBookingReturnCreatedBooking() {
        when(bookingService.create(anyLong(), any(BookingCreateDto.class))).thenReturn(bookingDto);

        BookingDto result = bookingController.create(1L, bookingCreateDto);

        assertEquals(bookingDto, result);
    }

    @Test
    void updateBookingReturnUpdatedBooking() {
        when(bookingService.update(anyLong(), anyLong(), any(Boolean.class))).thenReturn(bookingDto);

        BookingDto result = bookingController.update(1L, 1L, true);

        assertEquals(bookingDto, result);
    }

    @Test
    void findBookingByIdReturnBookingDto() {
        when(bookingService.getBookingById(anyLong(), anyLong())).thenReturn(bookingDto);

        BookingDto result = bookingController.findBookingById(1L, 1L);

        assertEquals(bookingDto, result);
    }

    @Test
    void findAllReturnListOfBookings() {
        when(bookingService.findAll(anyLong(), any(String.class), any(Integer.class), any(Integer.class)))
                .thenReturn(Collections.singletonList(bookingDto));

        List<BookingDto> result = bookingController.findAll(1L, "ALL", 0, 10);

        assertEquals(1, result.size());
        assertEquals(bookingDto, result.get(0));
    }

    @Test
    void getAllOwnerReturnListOfBookings() {
        when(bookingService.getOwnerBookings(anyLong(), any(String.class), any(Integer.class), any(Integer.class)))
                .thenReturn(Collections.singletonList(bookingDto));

        List<BookingDto> result = bookingController.getAllOwner(1L, "ALL", 0, 10);

        assertEquals(1, result.size());
        assertEquals(bookingDto, result.get(0));
    }
}