# Transparency in Financial Fraud Detection using Explainable AI

## Overview
This project implements a Machine Learning-based fraud detection system for real-time credit card transactions using Random Forest, XGBoost, and an Ensemble model. The system processes transaction data to detect fraudulent activities and immediately blocks suspicious transactions. It integrates Explainable AI (SHAP, LIME) to enhance transparency in decision-making and for managing financial transactions while proactively preventing fraud.


## Features
- Utilizes Random Forest, XGBoost, and an Ensemble model for accurate fraud detection.
- Integrates SHAP and LIME for explainable AI, providing insights into fraud detection decisions.
- Detects fraudulent transactions in real time and blocks them before processing.
- Maintains a comprehensive transaction log for transparency and accountability.
- Built with Android Jetpack and Kotlin, ensuring a seamless user experience.
  

## Dataset Details

The dataset used for this project is collected from Kaggle <b> “Bank Account Fraud Dataset” </b>. The dataset comprises a diverse collection of financial transaction records, specifically curated to detect fraudulent banking activities and enhance digital transaction security. It includes various transaction attributes such as transaction ID, user ID, transaction amount, location data, timestamp, and fraud labels (0 for legitimate, 1 for fraud). This dataset helps to make the model more versatile during training, ensuring accurate fraud detection and minimizing false positives and negatives. The dataset consists of 638,000 transactions, which are further split into 448,000 (70%) train, 127,000 (20%) validation, and 63,000 (10%) test samples.</div><br/>
The dataset can be accessed by using the link : https://www.kaggle.com/datasets/sgpjesus/bank-account-fraud-dataset-neurips-2022/data 


## Algorithms and Technologies
  
<b>1)Random Forest : </b> Ensemble learning method for high accuracy in fraud detection.

<b>2)XGBoost :</b> Optimized gradient boosting for handling large datasets efficiently.

<b>3)Ensemble method :</b> Combines multiple models for improved fraud detection performance.

<b>4)SHAP :</b> Identifies the contribution of each feature to the model’s decision.

<b>5)LIME :</b> Provides local explanations for individual fraud predictions.

<b>6)Android Jetpack & Kotlin :</b> Used for mobile application development.


## Steps for project execution

1)Download all the files.

2)Setup the Android app by installing dependencies using Android Studio.

3)Install required Python libraries (pandas, numpy, sklearn, xgboost).Train models on the provided dataset.

4)Integrate ML Model with Android App.The app sends transaction data to the backend.The model predicts if the transaction is fraudulent.

5)Now complete the registration and login.

6)Enter the transaction details to get an output that identifies the transaction as fraudulent or not.

<br/>

## Project Workflow

<div align="justify">

<b>1)Collection :</b> The Transaction data is collected from users and stored securely.

<b>2)Preprocessing :</b> The data undergo preprocessing steps such as cleaning, normalization and for training.

<b>3)Feature Extraction :</b> The machine learning model  extracts important features from the dataset such as location, amount, session time, accounter holder, type of cards.

<b>4)Model Training & Selection :</b> Train Random Forest, XGBoost, and an Ensemble Model on labeled fraud data and evaluate models on accuracy, recall, and F1-score.Select the best-performing model for deployment.

<b>5)Fraud Prediction in Real-Time :</b> A new transaction is passed through the trained model. The system classifies it as fraudulent or legitimate.Fraudulent transactions are blocked immediately.

<b>8)Output & Visualization :</b> The system displays the transaction as fraudulent or not helping users to analyze the results.</div>
