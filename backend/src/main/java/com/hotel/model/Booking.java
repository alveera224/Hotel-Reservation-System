package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    
    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;
    
    @Column(name = "check_out_date", nullable = false)
    private LocalDateTime checkOutDate;
    
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
    
    @Column(name = "booking_status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;
    
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    @Column(name = "payment_method")
    private String paymentMethod;
    
    @Column(name = "transaction_id")
    private String transactionId;
    
    @Column(name = "special_requests")
    private String specialRequests;
    
    @Column(name = "number_of_guests")
    private Integer numberOfGuests;
    
    @Column(name = "guest_names")
    private String guestNames;
    
    @Column(name = "cancellation_reason")
    private String cancellationReason;
    
    @Column(name = "cancellation_date")
    private LocalDateTime cancellationDate;
    
    @Column(name = "refund_amount")
    private BigDecimal refundAmount;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CHECKED_IN,
        CHECKED_OUT,
        CANCELLED,
        NO_SHOW
    }
    
    public enum PaymentStatus {
        PENDING,
        PAID,
        PARTIALLY_PAID,
        REFUNDED,
        FAILED
    }
} 