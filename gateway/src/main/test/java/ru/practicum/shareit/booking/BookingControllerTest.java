package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingClient bookingClient;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void createBookingReturnResponseFromClient() {
        Long userId = 1L;
        BookItemRequestDto requestDto = new BookItemRequestDto();
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Бронь создана", HttpStatus.OK);
        when(bookingClient.create(userId, requestDto)).thenReturn(expectedResponse);
        ResponseEntity<Object> actualResponse = bookingController.create(userId, requestDto);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingClient, times(1)).create(userId, requestDto);
    }

    @Test
    void updateBookingReturnResponseFromClient() {
        Long userId = 1L;
        Long bookingId = 2L;
        Boolean approved = true;

        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Бронь обновлена", HttpStatus.OK);
        when(bookingClient.update(userId, bookingId, approved)).thenReturn(expectedResponse);
        ResponseEntity<Object> actualResponse = bookingController.update(userId, bookingId, approved);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingClient, times(1)).update(userId, bookingId, approved);
    }

    @Test
    void getBookingsReturnResponseFromClient() {
        Long userId = 1L;
        String stateParam = "ALL";
        Integer from = 0;
        Integer size = 10;
        BookingState state = BookingState.ALL;

        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Получить всю информацию о бронированиях", HttpStatus.OK);
        when(bookingClient.getBookings(userId, state, from, size)).thenReturn(expectedResponse);
        ResponseEntity<Object> actualResponse = bookingController.getBookings(userId, stateParam, from, size);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingClient, times(1)).getBookings(userId, state, from, size);
    }

    @Test
    void getAllOwnerBookingsReturnResponseFromClient() {
        Long ownerId = 1L;
        String stateParam = "ALL";
        Integer from = 0;
        Integer size = 10;
        BookingState state = BookingState.ALL;

        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Получить всех обладателей бронирования ", HttpStatus.OK);
        when(bookingClient.getAllOwner(ownerId, state, from, size)).thenReturn(expectedResponse);
        ResponseEntity<Object> actualResponse = bookingController.getAllOwner(ownerId, stateParam, from, size);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingClient, times(1)).getAllOwner(ownerId, state, from, size);
    }

    @Test
    void getBookingReturnResponseFromClient() {
        Long userId = 1L;
        Long bookingId = 2L;

        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Получить информацию о бронировании", HttpStatus.OK);
        when(bookingClient.getBooking(userId, bookingId)).thenReturn(expectedResponse);
        ResponseEntity<Object> actualResponse = bookingController.getBooking(userId, bookingId);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingClient, times(1)).getBooking(userId, bookingId);
    }
}