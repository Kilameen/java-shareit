package ru.practicum.shareit.booking;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EntityManager em;

    private User booker;
    private Item item;
    private Booking booking;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        booker = new User();
        booker.setName("Booker");
        booker.setEmail("booker@example.com");
        em.persist(booker);

        item = new Item();
        item.setName("Item");
        item.setDescription("Description");
        item.setAvailable(true);

        User owner = new User();
        owner.setName("Owner");
        owner.setEmail("owner@example.com");
        em.persist(owner);
        item.setOwner(owner);
        em.persist(item);

        now = LocalDateTime.now();
        booking = new Booking();
        booking.setStart(now.plusDays(1));
        booking.setEnd(now.plusDays(2));
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(Status.WAITING);
        em.persist(booking);
    }

    @Test
    void findAllByUserBookings_emptyResult() {
        List<Booking> bookings = bookingRepository.findAllByUserBookings(booker.getId(), item.getId(), now.plusDays(3));
        assertTrue(bookings.isEmpty());
    }

    @Test
    void findOverlappingBookings_emptyResult() {
        List<Booking> bookings = bookingRepository.findOverlappingBookings(item.getId(), now.plusDays(3), now.plusDays(4));
        assertTrue(bookings.isEmpty());
    }

    @Test
    void findAllByItemInAndStatusOrderByStartAsc_emptyResult() {
        List<Item> items = List.of(item);
        List<Booking> bookings = bookingRepository.findAllByItemInAndStatusOrderByStartAsc(items, Status.APPROVED);
        assertTrue(bookings.isEmpty());
    }

    @Test
    void findAllByItemAndStatusOrderByStartAsc_emptyResult() {
        List<Booking> bookings = bookingRepository.findAllByItemAndStatusOrderByStartAsc(item, Status.APPROVED);
        assertTrue(bookings.isEmpty());
    }

    @Test
    void findByBookerIdOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByBookerIdOrderByStartDesc(booker.getId());
        assertEquals(1, bookings.size());
    }

    @Test
    void findByBookerIdAndEndBeforeOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByBookerIdAndEndBeforeOrderByStartDesc(booker.getId(), now.plusDays(3));
        assertEquals(1, bookings.size());
    }

    @Test
    void findByBookerIdAndStartAfterOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByBookerIdAndStartAfterOrderByStartDesc(booker.getId(), now);
        assertEquals(1, bookings.size());
    }

    @Test
    void findByBookerIdAndStatusAndStartAfterOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByBookerIdAndStatusAndStartAfterOrderByStartDesc(booker.getId(), Status.WAITING, now);
        assertEquals(1, bookings.size());
    }

    @Test
    void findByBookerIdAndStatusOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByBookerIdAndStatusOrderByStartDesc(booker.getId(), Status.WAITING);
        assertEquals(1, bookings.size());
    }

    @Test
    void findByItemOwnerIdOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByItemOwnerIdOrderByStartDesc(item.getOwner().getId());
        assertEquals(1, bookings.size());
    }

    @Test
    void findByItemOwnerIdAndEndBeforeOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByItemOwnerIdAndEndBeforeOrderByStartDesc(item.getOwner().getId(), now.plusDays(3));
        assertEquals(1, bookings.size());
    }

    @Test
    void findByItemOwnerIdAndStartAfterOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByItemOwnerIdAndStartAfterOrderByStartDesc(item.getOwner().getId(), now);
        assertEquals(1, bookings.size());
    }

    @Test
    void findByItemOwnerIdAndStatusAndStartAfterOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByItemOwnerIdAndStatusAndStartAfterOrderByStartDesc(item.getOwner().getId(), Status.WAITING, now);
        assertEquals(1, bookings.size());
    }

    @Test
    void findByItemOwnerIdAndStatusOrderByStartDesc() {
        List<Booking> bookings = bookingRepository.findByItemOwnerIdAndStatusOrderByStartDesc(item.getOwner().getId(), Status.WAITING);
        assertEquals(1, bookings.size());
    }
}