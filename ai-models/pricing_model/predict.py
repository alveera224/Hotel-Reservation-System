from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import numpy as np
from datetime import datetime
import pandas as pd
import os

app = Flask(__name__)
CORS(app)

# Load the trained models and preprocessors
MODEL_PATH = 'pricing_model.joblib'
SCALER_PATH = 'feature_scaler.joblib'
FEATURE_COLUMNS_PATH = 'feature_columns.joblib'
CATEGORY_ENCODER_PATH = 'hotel_category_encoder.joblib'
ROOM_TYPE_ENCODER_PATH = 'room_type_encoder.joblib'
LOCATION_ENCODER_PATH = 'location_encoder.joblib'

model = None
scaler = None
feature_columns = None
category_encoder = None
room_type_encoder = None
location_encoder = None

def load_models():
    global model, scaler, feature_columns, category_encoder, room_type_encoder, location_encoder
    
    if os.path.exists(MODEL_PATH):
        model = joblib.load(MODEL_PATH)
        scaler = joblib.load(SCALER_PATH)
        feature_columns = joblib.load(FEATURE_COLUMNS_PATH)
        category_encoder = joblib.load(CATEGORY_ENCODER_PATH)
        room_type_encoder = joblib.load(ROOM_TYPE_ENCODER_PATH)
        location_encoder = joblib.load(LOCATION_ENCODER_PATH)
    else:
        raise FileNotFoundError(f"Model files not found. Please train the model first.")

def prepare_features(data):
    """Prepare features for prediction."""
    # Convert booking date to datetime
    booking_date = pd.to_datetime(data['booking_date'])
    
    # Extract date features
    month = booking_date.month
    day_of_week = booking_date.dayofweek
    is_weekend = int(day_of_week in [5, 6])
    is_holiday_season = int(month in [12, 7, 8])
    
    # Encode categorical features
    hotel_category_encoded = category_encoder.transform([data['hotel_category']])[0]
    room_type_encoded = room_type_encoder.transform([data['room_type']])[0]
    location_encoded = location_encoder.transform([data['location']])[0]
    
    # Create feature array
    features = np.array([
        hotel_category_encoded,
        room_type_encoded,
        location_encoded,
        month,
        day_of_week,
        is_weekend,
        is_holiday_season,
        data['avg_monthly_occupancy'],
        data['price_vs_competition'],
        data['historical_demand'],
        data['room_capacity'],
        data['has_view'],
        data['has_balcony']
    ]).reshape(1, -1)
    
    # Scale features
    features_scaled = scaler.transform(features)
    
    return features_scaled

@app.route('/predict', methods=['POST'])
def predict_price():
    try:
        data = request.get_json()
        
        if not data:
            return jsonify({'error': 'No data provided'}), 400
        
        # Prepare features
        features = prepare_features(data)
        
        # Make prediction
        predicted_price = model.predict(features)[0]
        
        # Apply business rules and adjustments
        base_price = predicted_price
        
        # Adjust for high demand periods
        if data['avg_monthly_occupancy'] > 0.8:  # High occupancy
            predicted_price *= 1.1
        
        # Adjust for competition
        if data['price_vs_competition'] < 0.9:  # Our price is significantly lower
            predicted_price *= 1.05
        
        return jsonify({
            'predicted_price': float(predicted_price),
            'base_price': float(base_price),
            'confidence': float(model.predict_proba(features)[0] if hasattr(model, 'predict_proba') else 0.8)
        })
    
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/health', methods=['GET'])
def health_check():
    return jsonify({
        'status': 'healthy',
        'model_loaded': model is not None,
        'preprocessors_loaded': all([
            scaler is not None,
            feature_columns is not None,
            category_encoder is not None,
            room_type_encoder is not None,
            location_encoder is not None
        ])
    })

if __name__ == '__main__':
    load_models()
    app.run(host='0.0.0.0', port=5002) 