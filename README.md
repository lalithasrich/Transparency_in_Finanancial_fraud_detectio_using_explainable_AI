# Transparency in Financial Fraud Detection Using Explainable AI
 
 ## Overview
 This project presents a real-time financial fraud detection system using advanced machine learning models like Random Forest, XGBoost, Logistic Regression, KNN, SVM and Neural Networks. It leverages Explainable AI (XAI) tools-SHAP and LIME-for transparent decision-making. Users can register, initiate transactions, and receive fraud predictions with clear visual explanations. The system is built using Python, PostgreSQL, and Streamlit for seamless web deployment. It also integrates robust authentication and transaction logging features to ensure data security.
 
 
 ## Features
 - Utilizes Random Forest, XGBoost, Logistic Regression, KNN, SVM and Neural Networks for accurate fraud detection.
 - Integrates SHAP and LIME for explainable AI, providing insights into fraud detection decisions.
 - Detects fraudulent transactions in real time and blocks them before processing.
 - Maintains a comprehensive transaction log for transparency and accountability.
 - Built using Python, PostgreSQL, and Streamlit for efficient and interactive web deployment.
   
 
 ## Dataset Details
 
 The dataset used for this project is collected from Kaggle <b> “Bank Account Fraud Dataset” </b>. The dataset comprises a diverse collection of financial transaction records, specifically curated to detect fraudulent banking activities and enhance digital transaction security. It includes various transaction attributes such as transaction ID, user ID, transaction amount, location data, timestamp, and fraud labels (0 for legitimate, 1 for fraud). This dataset helps to make the model more versatile during training, ensuring accurate fraud detection and minimizing false positives and negatives. The dataset consists of 638,000 transactions, which are further split into 448,000 (70%) train, 127,000 (20%) validation, and 63,000 (10%) test samples.</div><br/>
 The dataset can be accessed by using the link : https://www.kaggle.com/datasets/sgpjesus/bank-account-fraud-dataset-neurips-2022/data 
 
 
 ## Algorithms and Technologies
   
 <b>1)Random Forest : </b> Ensemble learning method for high accuracy in fraud detection.
 
 <b>2)XGBoost :</b> Optimized gradient boosting for handling large datasets efficiently.
 
 <b>3)Neural Network :</b> For learning complex, non-linear transaction patterns.
 
 <b>4)SHAP :</b> Identifies the contribution of each feature to the model’s decision.
 
 <b>5)LIME :</b> Provides local explanations for individual fraud predictions.
 
 <b>6)PostgreSQL :</b> Relational database for securely storing user and transaction data.

 <b>7)Streamlit :</b> Used to build an interactive and user-friendly web application.
 
 
 ## Steps for project execution
 
 1) Install required libraries and tools including Python, Streamlit and PostgreSQL.

 2) Collect and preprocess data from the Kaggle “Bank Account Fraud Dataset”.

 3) Handle missing values, encode categorical data, balance classes using SMOTE, and normalize features.

 4) Train machine learning models: Logistic Regression, KNN, Decision Tree, Random Forest, XGBoost, SVM, and Neural Network.

 5) Evaluate models using metrics like accuracy, precision, and recall to select the best-performing model.

 6) Integrate SHAP and LIME for explainable AI to interpret model predictions.

 7) Develop a frontend interface using Streamlit for user interaction.

 8) Set up backend services using PostgreSQL for data management and authentication.

 9) Enable users to register, log in, initiate transactions, and receive fraud prediction results.

10) Predict fraud in real-time using machine learning models and display whether the transaction is accepted or rejected based on the prediction.
 
 
 ## Project Workflow
 
 <div align="justify">
 
 <b>1)Collection :</b> The Transaction data is collected from users and stored securely.
 
 <b>2)Preprocessing :</b> The data undergo preprocessing steps such as cleaning, normalization and for training.
 
 <b>3)Feature Extraction :</b> The machine learning model extracts important features from the dataset such as location, amount, session time, accounter holder, type of cards.
 
 <b>4)Model Training & Selection :</b> Train Random Forest, XGBoost, Logistic Regression, KNN, SVM and Neural Networks on labeled fraud data and evaluate models on accuracy, recall, and F1-score.Select the best-performing model for deployment.
 
 <b>5)Fraud Prediction in Real-Time :</b> A new transaction is passed through the trained model. The system classifies it as fraudulent or legitimate.Fraudulent transactions will be dispalyed as Transaction rejected.
 
 <b>6)Output & Visualization :</b> The system displays the "Transaction rejected ,contact support for queries" if fraudulent and "Transaction successful" if legitimate.</div>
