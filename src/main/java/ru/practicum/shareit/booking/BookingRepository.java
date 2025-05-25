package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

    public interface BookingRepository extends JpaRepository<Booking, Long> {

        //ALL state by bookerId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.booker_id = ?1 " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findAllBookingByBookerId(Long bookerId);

        //CURRENT state by bookingId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.booker_id = ?1 " +
                "AND ?2 BETWEEN b.start_date AND b.end_date " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findCurrentBookingByBookerId(Long bookerId, LocalDateTime time);

        //PAST state by bookingId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.booker_id = ?1 AND b.end_date < ?2 " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findPastBookingByBookerId(Long bookerId, LocalDateTime time);

        //FUTURE state by bookingId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.booker_id = ?1 AND b.start_date > ?2 " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findFutureBookingByBookerId(Long bookerId, LocalDateTime time);

        //WAITING state by bookingId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.booker_id = ?1 AND b.status = 'WAITING' AND b.start_date > ?2 " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findWaitingBookingByBookerId(Long bookerId, LocalDateTime time);

        //REJECT state by bookingId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.booker_id = ?1 AND b.status = 'REJECTED' " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findRejectBookingByBookerId(Long bookerId, LocalDateTime time);

        //ALL state by ownerId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE i.owner_id = ?1 " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findAllBookingsByOwnerId(Long ownerId);

        //CURRENT state by ownerId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE i.owner_id = ?1 AND ?2 BETWEEN b.start_date AND b.end_date " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findAllCurrentBookingsByOwnerId(Long ownerId, LocalDateTime currentTime);

        //PAST state by ownerId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE i.owner_id = ?1 AND b.end_date < ?2 " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findAllPastBookingsByOwnerId(Long ownerId, LocalDateTime currentTime);

        //FUTURE state by ownerId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE i.owner_id = ?1 AND b.start_date > ?2 " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findAllFutureBookingsByOwnerId(Long ownerId, LocalDateTime currentTime);

        //WAITING state by ownerId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE i.owner_id = ?1 AND b.status = 'WAITING' AND b.start_date > ?2 " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findAllWaitingBookingsByOwnerId(Long ownerId, LocalDateTime currentTime);

        //REJECT state by ownerId
        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE i.owner_id = ?1 AND b.status = 'REJECTED' " +
                "ORDER BY b.start_date DESC", nativeQuery = true)
        List<Booking> findAllRejectedBookingsByOwnerId(Long ownerId);

        @Query(value = "SELECT * FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.item_id = ?1 " +
                "AND b.start_date < ?2 " +
                "AND b.status = 'APPROVED' " +
                "ORDER BY b.start_date DESC LIMIT 1 ", nativeQuery = true)
        Optional<Booking> getLastBooking(Long idItem, LocalDateTime currentTime);

        @Query(value = "SELECT * FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.item_id = ?1 AND b.start_date > ?2 AND b.status = 'APPROVED' " +
                "ORDER BY b.start_date ASC LIMIT 1 ", nativeQuery = true)
        Optional<Booking> getNextBooking(Long idItem, LocalDateTime currentTime);

        @Query(value = "SELECT b.* FROM bookings as b " +
                "JOIN items as i ON i.id = b.item_id " +
                "WHERE b.booker_id = ?1 AND i.id = ?2 AND b.status = 'APPROVED' AND b.end_date < ?3 ", nativeQuery = true)
        List<Booking> findAllByUserBookings(Long userId, Long itemId, LocalDateTime now);

        List<Booking> findAllByItemInAndStatusOrderByStartAsc(List<Item> items, Status status);

        List<Booking> findAllByItemAndStatusOrderByStartAsc(Item item, Status bookingStatus);
    }
