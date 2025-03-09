package com.hotel.repository;

import com.hotel.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByHotelId(Long hotelId);
    
    List<Review> findByUserId(Long userId);
    
    @Query("SELECT r FROM Review r WHERE r.hotel.id = :hotelId ORDER BY r.createdAt DESC")
    List<Review> findLatestReviewsByHotelId(@Param("hotelId") Long hotelId);
    
    @Query("SELECT r FROM Review r WHERE r.hotel.id = :hotelId AND r.rating >= :minRating")
    List<Review> findByHotelIdAndMinRating(
        @Param("hotelId") Long hotelId,
        @Param("minRating") Integer minRating
    );
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.hotel.id = :hotelId")
    Double calculateAverageRating(@Param("hotelId") Long hotelId);
    
    @Query("SELECT r.sentimentLabel, COUNT(r) FROM Review r " +
           "WHERE r.hotel.id = :hotelId " +
           "GROUP BY r.sentimentLabel")
    List<Object[]> getSentimentDistribution(@Param("hotelId") Long hotelId);
    
    @Query("SELECT r FROM Review r " +
           "WHERE r.hotel.id = :hotelId " +
           "AND r.verified = true " +
           "ORDER BY r.likesCount DESC")
    List<Review> findTopReviewsByLikes(@Param("hotelId") Long hotelId);
    
    @Query("SELECT COUNT(r) FROM Review r " +
           "WHERE r.hotel.id = :hotelId " +
           "AND r.sentimentLabel = :sentiment")
    Long countBySentiment(
        @Param("hotelId") Long hotelId,
        @Param("sentiment") String sentiment
    );
} 