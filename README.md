# AI-Powered Hotel Reservation System 🏨

A sophisticated hotel reservation platform that combines traditional booking features with AI-powered dynamic pricing and sentiment analysis.

## 🌟 Features

- **AI-Powered Dynamic Pricing**
  - XGBoost model for real-time price predictions
  - Factors: seasonal demand, competitor pricing, room type, booking trends
  
- **Sentiment Analysis for Reviews**
  - TF-IDF + SVM model for review classification
  - Categories: Positive, Neutral, Negative
  - Trained on Booking.com hotel reviews dataset

- **Hotel Search & Booking**
  - User-friendly React.js interface
  - Real-time availability checking
  - Secure payment processing
  
- **Smart Database Management**
  - SQL database with JDBC connectivity
  - Efficient data handling and retrieval

## 🛠️ Tech Stack

- **Frontend**: React.js
- **Backend**: Java (Spring Boot)
- **Database**: MySQL/PostgreSQL
- **AI Models**: Python (Flask API)
  - XGBoost for pricing
  - TF-IDF + SVM for sentiment analysis
- **Dataset**: Booking.com Hotel Reviews

## 📁 Project Structure

```
hotel-reservation-system/
├── backend/                 # Java Backend
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── frontend/               # React Frontend
│   ├── src/
│   ├── package.json
│   └── README.md
├── ai-models/              # Python AI Services
│   ├── pricing_model/
│   │   ├── train.py
│   │   └── predict.py
│   ├── sentiment_model/
│   │   ├── train.py
│   │   └── predict.py
│   └── requirements.txt
└── database/              # Database Scripts
    └── schema.sql
```

## 🚀 Setup Instructions

### Prerequisites
- Java JDK 17+
- Node.js 16+
- Python 3.8+
- MySQL/PostgreSQL

### Backend Setup
1. Navigate to `backend/`
2. Run `mvn install`
3. Configure `application.properties`
4. Run `mvn spring-boot:run`

### Frontend Setup
1. Navigate to `frontend/`
2. Run `npm install`
3. Configure `.env`
4. Run `npm start`

### AI Models Setup
1. Navigate to `ai-models/`
2. Create virtual environment: `python -m venv venv`
3. Activate venv and install requirements:
   ```bash
   source venv/bin/activate  # Unix
   venv\Scripts\activate     # Windows
   pip install -r requirements.txt
   ```
4. Download dataset from Kaggle
5. Train models:
   ```bash
   python pricing_model/train.py
   python sentiment_model/train.py
   ```

## 🔄 API Endpoints

### Java Backend APIs
- `POST /api/hotels/search`
- `POST /api/bookings/create`
- `POST /api/reviews/submit`
- `GET /api/hotels/{id}/price`

### AI Model APIs
- `POST /api/ml/predict-price`
- `POST /api/ml/analyze-sentiment`

## 📊 Database Schema

Core tables:
- Hotels
- Rooms
- Users
- Bookings
- Reviews
- PricingHistory

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## 📝 License

MIT License - see LICENSE.md

## 🔗 Dataset Attribution

Hotel Reviews dataset from Booking.com:
https://www.kaggle.com/datasets/michelhatab/hotel-reviews-bookingcom 