package com.hotel.repository;

import com.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    
    List<Hotel> findByLocationContainingIgnoreCase(String location);
    
    List<Hotel> findByCategoryAndStarRating(String category, Integer starRating);
    
    @Query("SELECT h FROM Hotel h WHERE h.location = :location AND h.isActive = true")
    List<Hotel> findActiveHotelsByLocation(@Param("location") String location);
    
    @Query("SELECT h FROM Hotel h WHERE h.averageRating >= :minRating")
    List<Hotel> findByMinimumRating(@Param("minRating") Double minRating);
    
    @Query("SELECT h FROM Hotel h " +
           "WHERE h.location LIKE %:location% " +
           "AND (:category IS NULL OR h.category = :category) " +
           "AND (:minRating IS NULL OR h.averageRating >= :minRating) " +
           "AND h.isActive = true")
    List<Hotel> searchHotels(
        @Param("location") String location,
        @Param("category") String category,
        @Param("minRating") Double minRating
    );
    
    @Query("SELECT DISTINCT h.location FROM Hotel h")
    List<String> findAllLocations();
    
    @Query("SELECT DISTINCT h.category FROM Hotel h")
    List<String> findAllCategories();
} 