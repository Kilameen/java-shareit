package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Возвращает список всех бронирований для указанного арендатора (booker) отсортированных по дате начала в обратном порядке.
     * @param bookerId ID арендатора.
     * @return Список бронирований.
     */
    List<Booking> findByBookerIdOrderByStartDesc(Long bookerId);

    /**
     * Возвращает список текущих бронирований для указанного арендатора, то есть бронирований, которые начались до `end` и закончатся после `start`.
     * @param bookerId ID арендатора.
     * @param end Дата и время для сравнения.
     * @param start Дата и время для сравнения.
     * @return Список бронирований.
     */
    List<Booking> findByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId, LocalDateTime end, LocalDateTime start);

    /**
     * Возвращает список завершенных бронирований для указанного арендатора.
     * @param bookerId ID арендатора.
     * @param end Дата и время окончания бронирования.
     * @return Список бронирований.
     */
    List<Booking> findByBookerIdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime end);

    /**
     * Возвращает список будущих бронирований для указанного арендатора.
     * @param bookerId ID арендатора.
     * @param start Дата и время начала бронирования.
     * @return Список бронирований.
     */
    List<Booking> findByBookerIdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime start);

    /**
     * Возвращает список бронирований в статусе WAITING для указанного арендатора.
     * @param bookerId ID арендатора.
     * @param status Статус бронирования (WAITING).
     * @param start Дата и время начала бронирования.
     * @return Список бронирований.
     */
    List<Booking> findByBookerIdAndStatusAndStartAfterOrderByStartDesc(Long bookerId, Status status, LocalDateTime start);

    /**
     * Возвращает список бронирований в статусе REJECTED для указанного арендатора.
     * @param bookerId ID арендатора.
     * @param status Статус бронирования (REJECTED).
     * @return Список бронирований.
     */
    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Long bookerId, Status status);

    /**
     * Возвращает список всех бронирований для указанного владельца вещи.
     */
    List<Booking> findByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long ownerId, LocalDateTime end, LocalDateTime start);

    List<Booking> findByItemOwnerIdAndEndBeforeOrderByStartDesc(Long ownerId, LocalDateTime end);

    List<Booking> findByItemOwnerIdAndStartAfterOrderByStartDesc(Long ownerId, LocalDateTime start);

    List<Booking> findByItemOwnerIdAndStatusAndStartAfterOrderByStartDesc(Long ownerId, Status status, LocalDateTime start);

    List<Booking> findByItemOwnerIdAndStatusOrderByStartDesc(Long ownerId, Status status);

    /**
     *  Возвращает последнее бронирование для указанной вещи, которое уже началось.
     */
    @Query(value = "SELECT * FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.item_id = ?1 " +
            "AND b.start_date < ?2 " +
            "AND b.status = 'APPROVED' " +
            "ORDER BY b.start_date DESC LIMIT 1 ", nativeQuery = true)
    Optional<Booking> getLastBooking(Long idItem, LocalDateTime currentTime);

    /**
     * Возвращает ближайшее будущее бронирование для указанной вещи.
     */
    @Query(value = "SELECT * FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.item_id = ?1 AND b.start_date > ?2 AND b.status = 'APPROVED' " +
            "ORDER BY b.start_date ASC LIMIT 1 ", nativeQuery = true)
    Optional<Booking> getNextBooking(Long idItem, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.booker_id = ?1 AND i.id = ?2 AND b.status = 'APPROVED' AND b.end_date < ?3 ", nativeQuery = true)
    List<Booking> findAllByUserBookings(Long userId, Long itemId, LocalDateTime now);

    /**
     * Находит все пересекающиеся бронирования для указанного item
     */
    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.id = ?1 " +
            "AND b.start < ?3 " +
            "AND b.end > ?2")
    List<Booking> findOverlappingBookings(Long itemId, LocalDateTime start, LocalDateTime end);

    /**
     *  Возвращает список бронирований для списка вещей с указанным статусом.
     */
    List<Booking> findAllByItemInAndStatusOrderByStartAsc(List<Item> items, Status status);

    /**
     * Возвращает список бронирований для вещи с указанным статусом.
     */
    List<Booking> findAllByItemAndStatusOrderByStartAsc(Item item, Status bookingStatus);
}
