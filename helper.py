import numpy as np
import pandas as pd
from sklearn.preprocessing import LabelEncoder
from sklearn.preprocessing import StandardScaler
import joblib
import sys

# Load the pre-trained model once
model = joblib.load("best_model.pkl")
scaler = StandardScaler()

# This helper function will process the features and make predictions
def preprocess_and_predict(customer_data, amount, payment_type):
    """
    This function takes customer data, transaction amount, and payment type,
    preprocesses the features, and returns a prediction using the model.
    """
    
    # Prepare the features list with 46 features in the correct order
    
    print("Customer Data:", customer_data)
    sys.stdout.flush()
    if 'income' not in customer_data:
        raise KeyError("Missing key 'income' in customer_data")


    features = [
        amount,  # Transaction amount
        customer_data['income'],  # Income
        customer_data['transaction_count'],  # Transaction count (previous transactions)
        customer_data['average_transaction_amount'],  # Average transaction amount
        customer_data['age'],  # Age
        customer_data['employment_status'],  # Employment Status
        customer_data['housing_status'],  # Housing Status
        payment_type,  # Payment type
        # Add the other 38 features (use zeros or appropriate default values for placeholders)
        *([0] * (46 - 8))  # Adding placeholders for the remaining features to reach 46 features
    ]
    
    # Convert to numpy array for model input
    features = np.array(features).reshape(1, -1)
    
    # Normalize numeric features like 'amount', 'income', etc.
    features[0, [0, 1, 2, 3]] = scaler.fit_transform(features[:, [0, 1, 2, 3]])[0]  # Normalize only numeric features
    
    # Encode categorical features like 'employment_status', 'housing_status', and 'payment_type'
    le = LabelEncoder()

    # Encode categorical features
    features[0, 5] = le.fit_transform([features[0, 5]])[0]  # 'employment_status'
    features[0, 6] = le.fit_transform([features[0, 6]])[0]  # 'housing_status'
    features[0, 7] = le.fit_transform([features[0, 7]])[0]  # 'payment_type'

    # Ensure all features are properly encoded and normalized
    # The model is expected to have exactly 46 features at this point
    
    # Make the prediction
    prediction = model.predict(features)[0]
    probability = model.predict_proba(features)[0][1] if hasattr(model, 'predict_proba') else None

    return prediction, probability
