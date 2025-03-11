**PROJECT DETAILS :**

<div align="justify">This project focuses on developing a secure Android application for managing credit card transactions while detecting fraudulent activities in real-time. It enables users to transfer funds securely with each transaction being recorded and analyzed to ensure transparency and reliability. Leveraging advanced machine learning models such as Random Forest, XGBoost, and an Ensemble model, the system examines transaction patterns to classify them as fraudulent or legitimate, immediately preventing suspicious transactions. The application is built using Android Jetpack components and Kotlin for an intuitive user experience, while machine learning models are implemented in Python with libraries like Pandas, NumPy, and Scikit-Learn to ensure efficient data processing and accurate fraud detection. Firebase is used for real-time database management, allowing seamless data storage and retrieval. The system also incorporates Explainable AI (XAI) techniques such as SHAP and LIME to provide insights into why certain transactions are flagged, improving user understanding and trust. Continuously learning from transaction data, the model adapts to emerging fraud patterns and expanded support for various digital payment methods, making it a robust solution for secure and efficient financial transactions.</div><br/>

**FEATURES :**

<div align="justify">

<b>1)User-Friendly Interface :</b> Designed for ease of use, allowing researchers and conservationists to utilize the system effectively.

<b>2)High Accuracy : </b> Utilizes advanced machine learning models to enhance the accuracy of financial fraud detection.

<b>3)Explainable AI (XAI) for Transparency :</b> Uses SHAP (Shapley Additive Explanations) and LIME (Local Interpretable Model-agnostic Explanations) to explain why a transaction was flagged as fraudulent.

<b>4)Real-Time Fraud Detection :</b> Uses machine learning models to classify transactions as fraudulent or legitimate instantly.Fraudulent transactions are halted before they proceed.

<b>5)Comprehensive Transaction Log :</b> The system maintains a detailed history of transactions for user reference and auditing.

<b>6)Scalability and Flexibility :</b> The lightweight nature of the proposed system, powered by MobileNet, offers scalability and flexibility. </div><br/>

**DATASET DETAILS :**

<div align="justify">The dataset used for this project is collected from Kaggle <b> “Bank Account Fraud Dataset” </b>. The dataset comprises a diverse collection of financial transaction records, specifically curated to detect fraudulent banking activities and enhance digital transaction security. It includes various transaction attributes such as transaction ID, user ID, transaction amount, location data, timestamp, and fraud labels (0 for legitimate, 1 for fraud). This dataset helps to make the model more versatile during training, ensuring accurate fraud detection and minimizing false positives and negatives. The dataset consists of 638,000 transactions, which are further split into 448,000 (70%) train, 127,000 (20%) validation, and 63,000 (10%) test samples.</div><br/>
The dataset can be accessed by using the link : https://www.kaggle.com/datasets/sgpjesus/bank-account-fraud-dataset-neurips-2022/data <br/><br/>

<br/>

**ALGORITHMS AND TECHNOLOGIES :**

<div align="justify">
  
<b>1)Random Forest : </b> Ensemble learning method for high accuracy in fraud detection.

<b>2)XGBoost :</b> Optimized gradient boosting for handling large datasets efficiently.

<b>3)Ensemble method :</b> Combines multiple models for improved fraud detection performance.

<b>4)SHAP :</b> Identifies the contribution of each feature to the model’s decision.

<b>5)LIME :</b> Provides local explanations for individual fraud predictions.

<b>6)Android Jetpack & Kotlin :</b> Used for mobile application development.

<b>7)HTML, CSS, and JavaScript :</b> Used to develop a web-based platform where users can upload transaction details and sign in to interact with the system.</div><br/>


**STEPS FOR PROJECT EXECUTION :**

1)Download all the files.

2)Setup the Android app by installing dependencies using Android Studio.

3)Install required Python libraries (pandas, numpy, sklearn, xgboost).Train models on the provided dataset.

4)Integrate ML Model with Android App.The app sends transaction data to the backend.The model predicts if the transaction is fraudulent.

5)Now complete the registration and login.

6)Enter the transaction details to get an output that identifies the transaction as fraudulent or not.

<br/>

**PROJECT WORKFLOW :**

<div align="justify">

<b>1)Collection :</b> The Transaction data is collected from users and stored securely.

<b>2)Preprocessing :</b> The data undergo preprocessing steps such as cleaning, normalization and for training.

<b>3)Feature Extraction :</b> The machine learning model  extracts important features from the dataset such as location, amount, session time, accounter holder, type of cards.

<b>4)Model Training & Selection :</b> Train Random Forest, XGBoost, and an Ensemble Model on labeled fraud data and evaluate models on accuracy, recall, and F1-score.Select the best-performing model for deployment.

<b>5)Fraud Prediction in Real-Time :</b> A new transaction is passed through the trained model. The system classifies it as fraudulent or legitimate.Fraudulent transactions are blocked immediately.

<b>8)Output & Visualization :</b> The system displays the transaction as fraudulent or not helping users to analyze the results.</div>
