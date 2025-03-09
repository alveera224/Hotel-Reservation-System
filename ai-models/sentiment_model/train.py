import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.svm import LinearSVC
from sklearn.pipeline import Pipeline
from sklearn.metrics import classification_report
import joblib
import nltk
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
import re

# Download required NLTK data
nltk.download('punkt')
nltk.download('stopwords')
nltk.download('wordnet')

def preprocess_text(text):
    """Clean and preprocess text data."""
    # Convert to lowercase
    text = text.lower()
    
    # Remove special characters and digits
    text = re.sub(r'[^a-zA-Z\s]', '', text)
    
    # Tokenization
    tokens = word_tokenize(text)
    
    # Remove stopwords and lemmatize
    stop_words = set(stopwords.words('english'))
    lemmatizer = WordNetLemmatizer()
    tokens = [lemmatizer.lemmatize(token) for token in tokens if token not in stop_words]
    
    return ' '.join(tokens)

def load_and_prepare_data(file_path):
    """Load and prepare the hotel reviews dataset."""
    # Read the dataset
    df = pd.read_csv(file_path)
    
    # Convert review scores to sentiment labels
    df['sentiment'] = df['review_score'].apply(lambda x: 
        'positive' if x >= 8 
        else 'negative' if x <= 4 
        else 'neutral')
    
    # Preprocess the review text
    print("Preprocessing review texts...")
    df['processed_review'] = df['review_text'].fillna('').apply(preprocess_text)
    
    return df

def train_sentiment_model(X_train, y_train):
    """Train the TF-IDF + SVM model."""
    # Create pipeline
    pipeline = Pipeline([
        ('tfidf', TfidfVectorizer(max_features=5000, ngram_range=(1, 2))),
        ('classifier', LinearSVC(random_state=42))
    ])
    
    # Train the model
    print("Training the model...")
    pipeline.fit(X_train, y_train)
    
    return pipeline

def main():
    # Set random seed for reproducibility
    np.random.seed(42)
    
    # Load and prepare data
    print("Loading and preparing data...")
    df = load_and_prepare_data('hotel_reviews.csv')
    
    # Split the data
    X_train, X_test, y_train, y_test = train_test_split(
        df['processed_review'],
        df['sentiment'],
        test_size=0.2,
        random_state=42
    )
    
    # Train the model
    model = train_sentiment_model(X_train, y_train)
    
    # Evaluate the model
    print("\nEvaluating the model...")
    y_pred = model.predict(X_test)
    print("\nClassification Report:")
    print(classification_report(y_test, y_pred))
    
    # Save the model
    print("\nSaving the model...")
    joblib.dump(model, 'sentiment_model.joblib')
    print("Model saved successfully!")

if __name__ == "__main__":
    main() 