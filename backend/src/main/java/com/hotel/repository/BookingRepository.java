package com.hotel.repository;

import com.hotel.model.Booking;
import com.hotel.model.Booking.BookingStatus;
import com.hotel.model.Booking.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByUserId(Long userId);
    
    List<Booking> findByRoomId(Long roomId);
    
    @Query("SELECT b FROM Booking b WHERE b.room.hotel.id = :hotelId")
    List<Booking> findByHotelId(@Param("hotelId") Long hotelId);
    
    @Query("SELECT b FROM Booking b " +
           "WHERE b.room.hotel.id = :hotelId " +
           "AND b.checkInDate >= :startDate " +
           "AND b.checkInDate <= :endDate")
    List<Booking> findBookingsByDateRange(
        @Param("hotelId") Long hotelId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT b FROM Booking b " +
           "WHERE b.status = :status " +
           "AND b.room.hotel.id = :hotelId")
    List<Booking> findByStatusAndHotelId(
        @Param("status") BookingStatus status,
        @Param("hotelId") Long hotelId
    );
    
    @Query("SELECT b FROM Booking b " +
           "WHERE b.paymentStatus = :paymentStatus " +
           "AND b.room.hotel.id = :hotelId")
    List<Booking> findByPaymentStatusAndHotelId(
        @Param("paymentStatus") PaymentStatus paymentStatus,
        @Param("hotelId") Long hotelId
    );
    
    @Query("SELECT COUNT(b) FROM Booking b " +
           "WHERE b.room.hotel.id = :hotelId " +
           "AND b.status = 'CONFIRMED' " +
           "AND b.checkInDate BETWEEN :startDate AND :endDate")
    Long countConfirmedBookings(
        @Param("hotelId") Long hotelId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT b FROM Booking b " +
           "WHERE b.status = 'CONFIRMED' " +
           "AND b.checkInDate = CURRENT_DATE")
    List<Booking> findTodayCheckIns();
    
    @Query("SELECT b FROM Booking b " +
           "WHERE b.status = 'CHECKED_IN' " +
           "AND b.checkOutDate = CURRENT_DATE")
    List<Booking> findTodayCheckOuts();
} 