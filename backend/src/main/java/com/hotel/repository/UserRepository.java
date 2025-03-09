package com.hotel.repository;

import com.hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByVerificationToken(String token);
    
    Optional<User> findByResetToken(String token);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> findByRole(@Param("role") String role);
    
    @Query("SELECT u FROM User u " +
           "WHERE u.enabled = true " +
           "AND u.verified = true " +
           "AND u.lastLogin < :threshold")
    List<User> findInactiveUsers(@Param("threshold") java.time.LocalDateTime threshold);
    
    @Query("SELECT u FROM User u " +
           "WHERE u.email LIKE %:searchTerm% " +
           "OR u.firstName LIKE %:searchTerm% " +
           "OR u.lastName LIKE %:searchTerm%")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT COUNT(u) FROM User u " +
           "WHERE u.createdAt >= :startDate " +
           "AND u.createdAt <= :endDate")
    Long countNewUsers(
        @Param("startDate") java.time.LocalDateTime startDate,
        @Param("endDate") java.time.LocalDateTime endDate
    );
    
    @Query("SELECT u FROM User u " +
           "LEFT JOIN u.bookings b " +
           "GROUP BY u " +
           "ORDER BY COUNT(b) DESC")
    List<User> findTopCustomersByBookings();
} 