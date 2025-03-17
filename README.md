# Hotel Reservation System

A full-stack web application for hotel reservations, built with Java (Backend) and HTML/CSS/JavaScript (Frontend).

## Features

- User Registration and Authentication
- Hotel Search by Location and Dates
- Room Booking System
- Booking Management
- Responsive User Interface

## Tech Stack

### Backend
- Java
- MySQL Database
- Maven for dependency management
- Servlets for API endpoints

### Frontend
- HTML5
- CSS3
- JavaScript (Vanilla)
- Responsive Design

## Project Structure

```
Hotel Reservation System/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── hotel/
│   │   │   │           ├── DatabaseConnection.java
│   │   │   │           ├── dao/
│   │   │   │           ├── filter/
│   │   │   │           ├── model/
│   │   │   │           ├── repository/
│   │   │   │           └── servlet/
│   │   │   └── webapp/
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── index.html
│   ├── styles.css
│   └── script.js
└── database/
    └── schema.sql
```

## Setup Instructions

### Prerequisites
- Java JDK 11 or higher
- MySQL Database
- Maven
- Web Server (Tomcat)

### Database Setup
1. Create a MySQL database
2. Run the SQL commands from `database/schema.sql`

### Backend Setup
1. Navigate to the backend directory
2. Update database credentials in `src/main/resources/application.properties`
3. Build the project:
   ```bash
   mvn clean package
   ```
4. Deploy the generated WAR file to Tomcat

### Frontend Setup
1. Navigate to the frontend directory
2. Open `index.html` in a web browser
3. The application will connect to the backend at `http://localhost:8080`

## API Endpoints

### User Management
- POST `/api/users` - Register new user
- POST `/api/users/login` - User login
- GET `/api/users/{id}` - Get user details

### Hotel Management
- GET `/api/hotels` - Get all hotels
- GET `/api/hotels/search` - Search hotels by location and dates
- GET `/api/hotels/{id}` - Get hotel details

### Booking Management
- POST `/api/bookings` - Create new booking
- GET `/api/bookings/user/{userId}` - Get user's bookings
- DELETE `/api/bookings/{id}` - Cancel booking

## Contributing
1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Author
Alveera Fatima 