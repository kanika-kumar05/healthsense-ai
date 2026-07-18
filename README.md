# 🩺 HealthSense AI – Android Health Risk Prediction System

> **An AI-powered Android application that predicts potential health risks using Machine Learning and provides personalized preventive health recommendations using Google Gemini AI.**

---

## 📖 Overview

HealthSense AI is an intelligent Android healthcare application developed to help users better understand their overall health by analyzing essential health parameters and predicting potential health risks.

The application combines the power of **Machine Learning**, **Artificial Intelligence**, and **Android development** to provide users with instant health-risk predictions along with personalized preventive suggestions.

Users manually enter their health information into the Android application. The data is securely sent to a **Flask REST API**, where a trained **Random Forest Classifier** predicts the user's health risk category. The prediction is then sent to **Google Gemini AI**, which generates personalized health recommendations based on the user's vitals and predicted risk level.

The project follows the **MVVM Architecture** with the **Repository Pattern**, making the application scalable, maintainable, and easy to extend.

> **Note:** HealthSense AI currently works as an Android application where users enter their health details manually. Integration with real smartwatch sensors is planned for future versions.

---

# ✨ Features

* 📱 Android application developed using Kotlin
* 🧠 Machine Learning-based health risk prediction
* 🤖 Personalized health suggestions using Google Gemini AI
* ☁️ Flask REST API deployed on Render
* 🔥 Firebase Email & Google Authentication
* 🏗️ MVVM Architecture with Repository Pattern
* 📊 Health prediction dashboard
* 👤 User profile management
* 📈 Health insights and prediction history
* 🌐 REST API communication using Retrofit
* ⚡ Real-time prediction results
* 📦 Modular and scalable architecture

---

# 🎯 Project Objective

The primary objective of HealthSense AI is to build an intelligent Android healthcare application capable of:

* Predicting possible health risks using Machine Learning
* Providing personalized preventive health suggestions using AI
* Delivering fast cloud-based health predictions through REST APIs
* Demonstrating the integration of Android, AI, Machine Learning, and Cloud technologies in a single application

---

# 🧠 Machine Learning Model

The Machine Learning model was trained using a synthetically generated healthcare dataset containing approximately **6,000 health records**.

The model uses a **Random Forest Classifier** to classify users into one of five health risk categories.

### 📥 Input Features

| Feature              | Description                 |
| -------------------- | --------------------------- |
| ❤️ Heart Rate        | Heart beats per minute      |
| 🫁 SpO₂              | Blood oxygen saturation (%) |
| 😴 Sleep Hours       | Average hours of sleep      |
| 😰 Stress Level      | Self-reported stress score  |
| 🌡️ Body Temperature  | Body temperature (°C)       |
| 👤 Age               | Age of the user             |
| 💓 Systolic BP       | Systolic blood pressure     |
| 💙 Diastolic BP      | Diastolic blood pressure    |

---

# 🏷️ Health Risk Categories

The model predicts one of the following health risk categories:

| Risk Category       | Description                                   |
| ------------------- | --------------------------------------------- |
| ❤️ Cardiac Risk     | Possible heart or blood-pressure related risk |
| 🫁 Respiratory Risk | Low oxygen saturation or respiratory issues   |
| 🌡️ Fever Risk      | Elevated body temperature detected            |
| 😰 Stress Risk      | High stress combined with insufficient sleep  |
| ✅ Normal            | Health parameters are within normal limits    |

---

# 📱 Android Application

The Android application serves as the primary interface for users.

### Users can:

* Register using Firebase Authentication
* Login using Email or Google Sign-In
* Enter health parameters
* View AI-predicted health risks
* Receive personalized preventive suggestions
* View health insights
* Manage personal health information

The application follows the **MVVM Architecture** to ensure clean code organization and maintainability.

---

# 🏛️ System Architecture

The complete system consists of four major components:

```text
User
   │
   ▼
Android Application (Kotlin + MVVM)
   │
   ▼
Flask REST API (Render)
   │
   ▼
Random Forest ML Model
   │
   ▼
Prediction Result
   │
   ▼
Google Gemini AI
   │
   ▼
Personalized Health Suggestions
```

---

# ⚙️ Technologies Used

## 📱 Android

* Kotlin
* Android Studio
* MVVM Architecture
* LiveData
* ViewModel
* Repository Pattern
* Retrofit
* SharedPreferences

## 🤖 Artificial Intelligence

* Google Gemini AI

## 🧠 Machine Learning

* Python
* Scikit-learn
* Random Forest Classifier
* StandardScaler
* LabelEncoder
* Pandas
* NumPy
* Pickle

## 🌐 Backend

* Flask
* Gunicorn
* REST API
* Render

## 🔥 Authentication

* Firebase Authentication
* Google Sign-In

## 🛠️ Development Tools

* Android Studio
* VS Code
* Postman
* Git
* GitHub

---

## 📂 Project Structure

```text
healthsense-ai/
│
├── .ipynb_checkpoints/
│
├── HealthSense-AI-main/                  # Android application
│   ├── app/                              # Android source code
│   ├── gradle/
│   │   └── wrapper/
│   ├── healthsense-api/                  # Android API module
│   ├── build.gradle
│   ├── gradle.properties
│   ├── gradlew
│   ├── gradlew.bat
│   ├── settings.gradle
│   └── README.md
│
├── app.py                               # Flask REST API
├── dataset1.ipynb                       # Dataset generation & model training
├── healthsense_model_final.pkl          # Trained Random Forest model
├── healthsense_synthetic_dataset.csv    # Synthetic healthcare dataset
├── scaler.pkl                           # StandardScaler
├── label_encoder.pkl                    # LabelEncoder
├── requirements.txt                     # Python dependencies
├── render.yaml                          # Render deployment configuration
└── README.md                            # Main project documentation
```

---

# 📄 File Description

| File                                 | Description                           |
| ------------------------------------ | ------------------------------------- |
| 📱 app/                              | Android application source code       |
| 🐍 app.py                            | Flask REST API                        |
| 📊 dataset1.ipynb                    | Dataset generation and model training |
| 📁 healthsense_synthetic_dataset.csv | Synthetic healthcare dataset          |
| 🧠 healthsense_model_final.pkl       | Trained Random Forest model           |
| ⚖️ scaler.pkl                        | Feature scaler                        |
| 🏷️ label_encoder.pkl                | Label encoder                         |
| 📦 requirements.txt                  | Required Python packages              |
| ☁️ render.yaml                       | Render deployment configuration       |
| 📖 README.md                         | Project documentation                 |

---

# 🔗 REST API Endpoints

## 🏠 Home Endpoint

```http
GET /
```

### Response

```json
{
  "message": "Welcome to HealthSense AI API"
}
```

---

## 🩺 Predict Health Risk

```http
POST /predict
```

### Sample Request

```json
{
  "heart_rate": 118,
  "spo2": 91,
  "sleep_hours": 5,
  "stress_level": 6,
  "body_temperature": 37.2,
  "age": 65,
  "systolic_bp": 155,
  "diastolic_bp": 95
}
```

### Sample Response

```json
{
  "prediction": "Cardiac Risk"
}
```

---

# 🧪 Testing the API

Using **Postman**:

1. Run the Flask application.
2. Open Postman.
3. Select **POST**.
4. Enter

```
POST https://healthsense-api-3.onrender.com/predict
```

5. Select

```
Body → Raw → JSON
```

6. Paste the sample JSON.

7. Click **Send**.

---

# ☁️ Deployment

The Flask backend is deployed using **Render**.

### Build Command

```bash
pip install -r requirements.txt
```

### Start Command

```bash
gunicorn app:app
```

Example API URL

```
https://healthsense-api-3.onrender.com
```

Prediction Endpoint

```
https://healthsense-api-3.onrender.com/predict
```

---

# 🚀 Future Enhancements

* ⌚ Integrate with a real smartwatch to automatically collect health parameters
* 📡 Support real-time wearable sensor monitoring
* 🩺 Train the model using real-world healthcare datasets
* 📈 Advanced health analytics and trend visualization
* 🗄️ Cloud database for long-term health records
* 🚨 Emergency contact and health alert system
* 🤖 More advanced personalized AI recommendations
* 🌍 Support multiple wearable devices
* 🌐 Deploy the complete HealthSense AI ecosystem on the cloud

---

# ⚠️ Disclaimer

HealthSense AI is an educational project developed for learning and demonstration purposes.

The predictions generated by the application and the AI-generated health suggestions **are not medical diagnoses** and should **not** be considered professional medical advice.

Always consult a qualified healthcare professional regarding any medical concerns.

---


# ⭐ If you like this project...

If you found this project useful, consider giving it a ⭐ on GitHub.

It motivates us to keep improving HealthSense AI and build even smarter healthcare solutions.

---



<p align="center">
  <b>🩺 HealthSense AI</b><br>
  <i>Predict Today • Prevent Tomorrow • Live Healthier ❤️</i>
</p>
