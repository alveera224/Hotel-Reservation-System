// Show selected section and hide others
function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => {
        section.classList.add('hidden');
    });
    document.getElementById(sectionId).classList.remove('hidden');
}

// Handle login form submission
document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = {
        email: formData.get('email'),
        password: formData.get('password')
    };

    try {
        const response = await fetch('http://localhost:8080/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const user = await response.json();
            localStorage.setItem('user', JSON.stringify(user));
            showSection('home');
            alert('Login successful!');
        } else {
            alert('Login failed. Please check your credentials.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred during login.');
    }
});

// Handle registration form submission
document.getElementById('register-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = {
        firstName: formData.get('firstName'),
        lastName: formData.get('lastName'),
        email: formData.get('email'),
        password: formData.get('password')
    };

    try {
        const response = await fetch('http://localhost:8080/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const user = await response.json();
            localStorage.setItem('user', JSON.stringify(user));
            showSection('home');
            alert('Registration successful!');
        } else {
            alert('Registration failed. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred during registration.');
    }
});

// Search hotels
async function searchHotels() {
    const location = document.getElementById('location').value;
    const checkIn = document.getElementById('check-in').value;
    const checkOut = document.getElementById('check-out').value;

    if (!location || !checkIn || !checkOut) {
        alert('Please fill in all search fields');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/hotels/search?location=${location}&checkIn=${checkIn}&checkOut=${checkOut}`);
        if (response.ok) {
            const hotels = await response.json();
            displayHotels(hotels);
            showSection('hotels');
        } else {
            alert('Failed to fetch hotels. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while searching for hotels.');
    }
}

// Display hotels in the hotels section
function displayHotels(hotels) {
    const hotelsList = document.getElementById('hotels-list');
    hotelsList.innerHTML = '';

    hotels.forEach(hotel => {
        const hotelCard = document.createElement('div');
        hotelCard.className = 'hotel-card';
        hotelCard.innerHTML = `
            <h3>${hotel.name}</h3>
            <p>${hotel.location}</p>
            <p>Price: $${hotel.pricePerNight}/night</p>
            <button onclick="bookHotel(${hotel.id})">Book Now</button>
        `;
        hotelsList.appendChild(hotelCard);
    });
}

// Book a hotel
async function bookHotel(hotelId) {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        alert('Please login to book a hotel');
        showSection('login');
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/bookings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId: user.id,
                hotelId: hotelId,
                checkIn: document.getElementById('check-in').value,
                checkOut: document.getElementById('check-out').value
            })
        });

        if (response.ok) {
            alert('Booking successful!');
            loadBookings();
            showSection('bookings');
        } else {
            alert('Booking failed. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while booking the hotel.');
    }
}

// Load user's bookings
async function loadBookings() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        document.getElementById('bookings-list').innerHTML = '<p>Please login to view your bookings</p>';
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/bookings/user/${user.id}`);
        if (response.ok) {
            const bookings = await response.json();
            displayBookings(bookings);
        } else {
            alert('Failed to load bookings. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while loading bookings.');
    }
}

// Display bookings in the bookings section
function displayBookings(bookings) {
    const bookingsList = document.getElementById('bookings-list');
    bookingsList.innerHTML = '';

    if (bookings.length === 0) {
        bookingsList.innerHTML = '<p>No bookings found</p>';
        return;
    }

    bookings.forEach(booking => {
        const bookingCard = document.createElement('div');
        bookingCard.className = 'booking-card';
        bookingCard.innerHTML = `
            <h3>${booking.hotel.name}</h3>
            <p>Check-in: ${new Date(booking.checkIn).toLocaleDateString()}</p>
            <p>Check-out: ${new Date(booking.checkOut).toLocaleDateString()}</p>
            <p>Status: ${booking.status}</p>
            <button onclick="cancelBooking(${booking.id})">Cancel Booking</button>
        `;
        bookingsList.appendChild(bookingCard);
    });
}

// Cancel a booking
async function cancelBooking(bookingId) {
    try {
        const response = await fetch(`http://localhost:8080/api/bookings/${bookingId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Booking cancelled successfully!');
            loadBookings();
        } else {
            alert('Failed to cancel booking. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while cancelling the booking.');
    }
}

// Load bookings when the bookings section is shown
document.querySelector('a[onclick="showSection(\'bookings\')"]').addEventListener('click', loadBookings);

// Initialize the page
document.addEventListener('DOMContentLoaded', () => {
    showSection('home');
    if (isLoggedIn()) {
        loadBookings();
    }
}); 