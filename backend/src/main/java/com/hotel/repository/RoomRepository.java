package com.hotel.repository;

import com.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    List<Room> findByHotelId(Long hotelId);
    
    List<Room> findByHotelIdAndRoomType(Long hotelId, String roomType);
    
    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND r.available = true")
    List<Room> findAvailableRoomsByHotelId(@Param("hotelId") Long hotelId);
    
    @Query("SELECT r FROM Room r " +
           "WHERE r.hotel.id = :hotelId " +
           "AND r.available = true " +
           "AND r.capacity >= :guests " +
           "AND r.id NOT IN (" +
           "    SELECT b.room.id FROM Booking b " +
           "    WHERE b.status NOT IN ('CANCELLED', 'CHECKED_OUT') " +
           "    AND (" +
           "        (b.checkInDate BETWEEN :checkIn AND :checkOut) OR " +
           "        (b.checkOutDate BETWEEN :checkIn AND :checkOut) OR " +
           "        (:checkIn BETWEEN b.checkInDate AND b.checkOutDate)" +
           "    )" +
           ")")
    List<Room> findAvailableRooms(
        @Param("hotelId") Long hotelId,
        @Param("checkIn") LocalDateTime checkIn,
        @Param("checkOut") LocalDateTime checkOut,
        @Param("guests") Integer guests
    );
    
    @Query("SELECT DISTINCT r.roomType FROM Room r WHERE r.hotel.id = :hotelId")
    List<String> findRoomTypesByHotelId(@Param("hotelId") Long hotelId);
    
    @Query("SELECT r FROM Room r " +
           "WHERE r.hotel.id = :hotelId " +
           "AND r.cleaningStatus = 'NEEDS_CLEANING'")
    List<Room> findRoomsNeedingCleaning(@Param("hotelId") Long hotelId);
    
    @Query("SELECT r FROM Room r " +
           "WHERE r.hotel.id = :hotelId " +
           "AND r.maintenanceNotes IS NOT NULL " +
           "AND r.maintenanceNotes <> ''")
    List<Room> findRoomsNeedingMaintenance(@Param("hotelId") Long hotelId);
} 