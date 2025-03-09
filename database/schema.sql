-- Create Database
CREATE DATABASE hotel_reservation;

-- Connect to database
\c hotel_reservation;

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Users Table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(20),
    profile_picture VARCHAR(255),
    verified BOOLEAN DEFAULT FALSE,
    verification_token VARCHAR(255),
    reset_token VARCHAR(255),
    reset_token_expiry TIMESTAMP,
    last_login TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User Roles Table
CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id),
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role)
);

-- Hotels Table
CREATE TABLE hotels (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT,
    address TEXT,
    contact_number VARCHAR(20),
    email VARCHAR(255),
    star_rating INTEGER,
    check_in_time VARCHAR(50),
    check_out_time VARCHAR(50),
    average_rating DECIMAL(3,2),
    total_reviews INTEGER DEFAULT 0,
    parking BOOLEAN DEFAULT FALSE,
    wifi BOOLEAN DEFAULT FALSE,
    restaurant BOOLEAN DEFAULT FALSE,
    pool BOOLEAN DEFAULT FALSE,
    gym BOOLEAN DEFAULT FALSE,
    spa BOOLEAN DEFAULT FALSE,
    cancellation_policy TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Hotel Images Table
CREATE TABLE hotel_images (
    hotel_id BIGINT REFERENCES hotels(id),
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (hotel_id, image_url)
);

-- Rooms Table
CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    hotel_id BIGINT REFERENCES hotels(id),
    room_number VARCHAR(50) NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    capacity INTEGER NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    current_price DECIMAL(10,2),
    available BOOLEAN DEFAULT TRUE,
    square_meters DECIMAL(6,2),
    has_view BOOLEAN DEFAULT FALSE,
    has_balcony BOOLEAN DEFAULT FALSE,
    smoking BOOLEAN DEFAULT FALSE,
    bed_type VARCHAR(50),
    bed_count INTEGER,
    wifi BOOLEAN DEFAULT TRUE,
    tv BOOLEAN DEFAULT TRUE,
    minibar BOOLEAN DEFAULT FALSE,
    air_conditioning BOOLEAN DEFAULT TRUE,
    safe BOOLEAN DEFAULT FALSE,
    description TEXT,
    cleaning_status VARCHAR(50),
    last_cleaned TIMESTAMP,
    maintenance_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (hotel_id, room_number)
);

-- Room Images Table
CREATE TABLE room_images (
    room_id BIGINT REFERENCES rooms(id),
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (room_id, image_url)
);

-- Room Price History Table
CREATE TABLE room_price_history (
    room_id BIGINT REFERENCES rooms(id),
    price DECIMAL(10,2) NOT NULL,
    date TIMESTAMP NOT NULL,
    PRIMARY KEY (room_id, date)
);

-- Bookings Table
CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    room_id BIGINT REFERENCES rooms(id),
    check_in_date TIMESTAMP NOT NULL,
    check_out_date TIMESTAMP NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    booking_status VARCHAR(20) DEFAULT 'PENDING',
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    transaction_id VARCHAR(255),
    special_requests TEXT,
    number_of_guests INTEGER,
    guest_names TEXT,
    cancellation_reason TEXT,
    cancellation_date TIMESTAMP,
    refund_amount DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reviews Table
CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    hotel_id BIGINT REFERENCES hotels(id),
    user_id BIGINT REFERENCES users(id),
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    sentiment_score DECIMAL(4,3),
    sentiment_label VARCHAR(20),
    processed_text TEXT,
    verified BOOLEAN DEFAULT FALSE,
    stay_date DATE,
    room_cleanliness INTEGER,
    staff_service INTEGER,
    location_rating INTEGER,
    value_for_money INTEGER,
    likes_count INTEGER DEFAULT 0,
    response_text TEXT,
    response_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_hotels_location ON hotels(location);
CREATE INDEX idx_hotels_category ON hotels(category);
CREATE INDEX idx_rooms_hotel_id ON rooms(hotel_id);
CREATE INDEX idx_bookings_user_id ON bookings(user_id);
CREATE INDEX idx_bookings_room_id ON bookings(room_id);
CREATE INDEX idx_bookings_dates ON bookings(check_in_date, check_out_date);
CREATE INDEX idx_reviews_hotel_id ON reviews(hotel_id);
CREATE INDEX idx_reviews_user_id ON reviews(user_id);

-- Create function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers for updated_at
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_hotels_updated_at
    BEFORE UPDATE ON hotels
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_rooms_updated_at
    BEFORE UPDATE ON rooms
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_bookings_updated_at
    BEFORE UPDATE ON bookings
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_reviews_updated_at
    BEFORE UPDATE ON reviews
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column(); 