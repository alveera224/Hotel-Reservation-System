import React from 'react';
import {
  Box,
  Container,
  Typography,
  Button,
  Grid,
  Card,
  CardContent,
  CardMedia,
  TextField,
  Paper,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = React.useState({
    location: '',
    checkIn: '',
    checkOut: '',
    guests: 1,
  });

  const handleSearch = () => {
    navigate('/hotels', { state: searchParams });
  };

  // Featured hotels data (mock)
  const featuredHotels = [
    {
      id: 1,
      name: 'Luxury Resort & Spa',
      location: 'Maldives',
      price: 299,
      image: 'https://source.unsplash.com/800x600/?resort,luxury',
    },
    {
      id: 2,
      name: 'City View Hotel',
      location: 'New York',
      price: 199,
      image: 'https://source.unsplash.com/800x600/?hotel,city',
    },
    {
      id: 3,
      name: 'Mountain Retreat',
      location: 'Swiss Alps',
      price: 249,
      image: 'https://source.unsplash.com/800x600/?mountain,hotel',
    },
  ];

  return (
    <Box>
      {/* Hero Section */}
      <Box
        sx={{
          bgcolor: 'primary.main',
          color: 'white',
          py: 8,
          textAlign: 'center',
        }}
      >
        <Container maxWidth="lg">
          <Typography variant="h2" component="h1" gutterBottom>
            Find Your Perfect Stay
          </Typography>
          <Typography variant="h5" gutterBottom>
            AI-Powered Hotel Booking with Dynamic Pricing
          </Typography>
        </Container>
      </Box>

      {/* Search Section */}
      <Container maxWidth="lg" sx={{ mt: -4 }}>
        <Paper elevation={3} sx={{ p: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={3}>
              <TextField
                fullWidth
                label="Where are you going?"
                value={searchParams.location}
                onChange={(e) =>
                  setSearchParams({ ...searchParams, location: e.target.value })
                }
              />
            </Grid>
            <Grid item xs={12} sm={3}>
              <TextField
                fullWidth
                type="date"
                label="Check-in"
                InputLabelProps={{ shrink: true }}
                value={searchParams.checkIn}
                onChange={(e) =>
                  setSearchParams({ ...searchParams, checkIn: e.target.value })
                }
              />
            </Grid>
            <Grid item xs={12} sm={3}>
              <TextField
                fullWidth
                type="date"
                label="Check-out"
                InputLabelProps={{ shrink: true }}
                value={searchParams.checkOut}
                onChange={(e) =>
                  setSearchParams({ ...searchParams, checkOut: e.target.value })
                }
              />
            </Grid>
            <Grid item xs={12} sm={2}>
              <TextField
                fullWidth
                type="number"
                label="Guests"
                InputProps={{ inputProps: { min: 1 } }}
                value={searchParams.guests}
                onChange={(e) =>
                  setSearchParams({
                    ...searchParams,
                    guests: parseInt(e.target.value) || 1,
                  })
                }
              />
            </Grid>
            <Grid item xs={12} sm={1}>
              <Button
                fullWidth
                variant="contained"
                size="large"
                onClick={handleSearch}
                sx={{ height: '100%' }}
              >
                Search
              </Button>
            </Grid>
          </Grid>
        </Paper>
      </Container>

      {/* Featured Hotels Section */}
      <Container maxWidth="lg" sx={{ my: 8 }}>
        <Typography variant="h4" gutterBottom>
          Featured Hotels
        </Typography>
        <Grid container spacing={4}>
          {featuredHotels.map((hotel) => (
            <Grid item key={hotel.id} xs={12} sm={6} md={4}>
              <Card
                sx={{
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column',
                  cursor: 'pointer',
                }}
                onClick={() => navigate(`/hotels/${hotel.id}`)}
              >
                <CardMedia
                  component="img"
                  height="200"
                  image={hotel.image}
                  alt={hotel.name}
                />
                <CardContent>
                  <Typography gutterBottom variant="h5" component="h2">
                    {hotel.name}
                  </Typography>
                  <Typography color="text.secondary" paragraph>
                    {hotel.location}
                  </Typography>
                  <Typography variant="h6" color="primary">
                    ${hotel.price} / night
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Container>
    </Box>
  );
};

export default Home; 