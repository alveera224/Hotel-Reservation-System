# Hotel Reservation System

A simple hotel reservation system with a clean and intuitive interface.

## Features

- User Authentication (Login/Register)
- Hotel Search and Booking
- Room Management
- Booking Management
- Review System
- Admin Dashboard

## Tech Stack

### Backend
- Java Servlets
- JDBC for Database Operations
- PostgreSQL
- Gson for JSON Processing

### Frontend
- HTML5
- CSS3
- JavaScript (Vanilla)
- Fetch API for HTTP requests

### Database
- PostgreSQL

## Project Structure

```
hotel-reservation/
├── backend/              # Java Backend
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/hotel/
│   │       │       ├── servlet/    # HTTP request handlers
│   │       │       ├── dao/        # Database access objects
│   │       │       ├── model/      # Data models
│   │       │       └── DatabaseConnection.java
│   │       └── webapp/
│   │           └── WEB-INF/
│   │               └── web.xml
│   └── pom.xml
├── frontend/            # Simple HTML/CSS/JS Frontend
│   ├── index.html
│   ├── styles.css
│   └── script.js
├── database/            # Database Scripts
│   └── schema.sql
└── README.md
```

## Getting Started

### Prerequisites
- Java 17 or higher
- PostgreSQL 13 or higher
- Maven
- Tomcat 9 or higher
- Web browser

### Installation

1. Clone the repository:
```bash
git clone https://github.com/alveera224/Hotel-Reservation-System.git
cd Hotel-Reservation-System
```

2. Set up the database:
```bash
cd database
psql -U postgres -f schema.sql
```

3. Build and deploy the backend:
```bash
cd backend
mvn clean package
# Copy the generated WAR file to Tomcat's webapps directory
```

4. Open the frontend:
- Navigate to the `frontend` directory
- Open `index.html` in your web browser

The application will be available at:
- Frontend: Open `frontend/index.html` in your browser
- Backend: http://localhost:8080/hotel-reservation

## API Endpoints

### Users
- `POST /api/users` - Register a new user
- `GET /api/users` - Get all users
- `GET /api/users/{email}` - Get user by email
- `PUT /api/users` - Update user
- `DELETE /api/users/{id}` - Delete user

### Hotels
- `GET /api/hotels` - Get all hotels
- `GET /api/hotels/{id}` - Get hotel by ID
- `POST /api/hotels` - Create new hotel (Admin)
- `PUT /api/hotels/{id}` - Update hotel (Admin)
- `DELETE /api/hotels/{id}` - Delete hotel (Admin)

### Rooms
- `GET /api/rooms` - Get all rooms
- `GET /api/rooms/{id}` - Get room by ID
- `POST /api/rooms` - Create new room (Admin)
- `PUT /api/rooms/{id}` - Update room (Admin)
- `DELETE /api/rooms/{id}` - Delete room (Admin)

### Bookings
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `POST /api/bookings` - Create new booking
- `PUT /api/bookings/{id}` - Update booking
- `DELETE /api/bookings/{id}` - Cancel booking

### Reviews
- `GET /api/reviews` - Get all reviews
- `GET /api/reviews/{id}` - Get review by ID
- `POST /api/reviews` - Create new review
- `PUT /api/reviews/{id}` - Update review
- `DELETE /api/reviews/{id}` - Delete review

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 