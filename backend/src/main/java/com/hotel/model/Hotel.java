package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String location;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String address;
    private String contactNumber;
    private String email;
    
    @Column(name = "star_rating")
    private Integer starRating;
    
    @Column(name = "check_in_time")
    private String checkInTime;
    
    @Column(name = "check_out_time")
    private String checkOutTime;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Review> reviews;
    
    @Column(name = "average_rating")
    private Double averageRating;
    
    @Column(name = "total_reviews")
    private Integer totalReviews;
    
    private Boolean parking;
    private Boolean wifi;
    private Boolean restaurant;
    private Boolean pool;
    private Boolean gym;
    private Boolean spa;
    
    @Column(name = "image_urls")
    @ElementCollection
    private List<String> imageUrls;
    
    @Column(name = "cancellation_policy")
    private String cancellationPolicy;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = createdAt;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
} 