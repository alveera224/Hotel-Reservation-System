from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
from train import preprocess_text
import os

app = Flask(__name__)
CORS(app)

# Load the trained model
MODEL_PATH = 'sentiment_model.joblib'
model = None

def load_model():
    global model
    if os.path.exists(MODEL_PATH):
        model = joblib.load(MODEL_PATH)
    else:
        raise FileNotFoundError(f"Model file not found at {MODEL_PATH}. Please train the model first.")

@app.route('/predict', methods=['POST'])
def predict_sentiment():
    try:
        data = request.get_json()
        
        if not data or 'text' not in data:
            return jsonify({'error': 'No text provided'}), 400
        
        # Preprocess the input text
        processed_text = preprocess_text(data['text'])
        
        # Make prediction
        sentiment = model.predict([processed_text])[0]
        
        # Get probability scores
        probabilities = model.decision_function([processed_text])
        confidence = abs(probabilities[0])  # Using absolute value of decision function as confidence
        
        return jsonify({
            'sentiment': sentiment,
            'confidence': float(confidence),
            'processed_text': processed_text
        })
    
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/health', methods=['GET'])
def health_check():
    return jsonify({'status': 'healthy', 'model_loaded': model is not None})

if __name__ == '__main__':
    load_model()
    app.run(host='0.0.0.0', port=5001) 