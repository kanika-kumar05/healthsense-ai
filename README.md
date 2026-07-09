# 🩺 HealthSense API – AI-Powered Health Risk Prediction

HealthSense API is a Machine Learning–based healthcare risk prediction system that analyzes a user's basic health parameters and predicts a possible health risk category.

The system uses a trained **Random Forest Classifier** to classify health conditions into five categories:

* 🫀 Cardiac Risk
* 🫁 Respiratory Risk
* 🌡️ Fever Risk
* 😰 Stress Risk
* ✅ Normal

The trained Machine Learning model is integrated with a **Flask REST API**, allowing health data to be sent in JSON format and predictions to be received instantly.

---

## 🚀 Features

* Predicts health risks using Machine Learning
* Accepts health parameters through a REST API
* Provides real-time health risk predictions
* Uses a Random Forest classification model
* Supports five health risk categories
* Performs feature scaling using StandardScaler
* Converts prediction labels using LabelEncoder
* Returns predictions in JSON format
* Can be tested using Postman
* Can be deployed as a cloud-based API

---

## 🧠 Machine Learning Model

The model was trained using a synthetically generated healthcare dataset containing approximately **6,000 health records**.

### Input Features

| Feature            | Description                     |
| ------------------ | ------------------------------- |
| `heart_rate`       | Number of heartbeats per minute |
| `spo2`             | Blood oxygen saturation level   |
| `sleep_hours`      | Average sleep duration          |
| `stress_level`     | Self-reported stress level      |
| `body_temperature` | Body temperature                |
| `age`              | Age of the person               |
| `systolic_bp`      | Systolic blood pressure         |
| `diastolic_bp`     | Diastolic blood pressure        |

---

## 🏷️ Prediction Categories

The API predicts one of the following health risk categories:

| Prediction         | Description                                                   |
| ------------------ | ------------------------------------------------------------- |
| `Cardiac Risk`     | Indicates a possible heart- or blood-pressure-related risk    |
| `Respiratory Risk` | Indicates a possible oxygen-level or respiratory-related risk |
| `Fever Risk`       | Indicates an elevated body temperature                        |
| `Stress Risk`      | Indicates high stress combined with insufficient sleep        |
| `Normal`           | Health parameters are within the expected ranges              |

---

## 🛠️ Technologies Used

* Python
* Flask
* Scikit-learn
* Pandas
* NumPy
* Random Forest Classifier
* StandardScaler
* LabelEncoder
* Pickle
* Postman
* Render

---

## 📁 Project Structure

```text
healthsense-api/
│
├── .ipynb_checkpoints/
│
├── HealthSense-AI-main/                # Android application
│   │
│   ├── app/                            # Android source code and resources
│   │
│   ├── gradle/
│   │   └── wrapper/                    # Gradle wrapper configuration
│   │
│   ├── healthsense-api/                # Additional API-related files
│   ├── build.gradle                    # Project-level Gradle configuration
│   ├── gradle.properties               # Gradle project properties
│   ├── gradlew                         # Gradle executable for Linux/macOS
│   ├── gradlew.bat                     # Gradle executable for Windows
│   └── settings.gradle                 # Android project settings
│
├── app.py                              # Flask REST API
├── dataset1.ipynb                      # Dataset generation and model training
├── healthsense_model_final.pkl         # Trained Machine Learning model
├── healthsense_synthetic_dataset.csv   # Synthetic healthcare dataset
├── label_encoder.pkl                   # Saved LabelEncoder
├── scaler.pkl                          # Saved StandardScaler
├── requirements.txt                    # Python dependencies
├── render.yaml                         # Application configuration file
└── README.md                           # Project documentation
```

### File Description

* `HealthSense-AI-main/` – Contains the complete Android mobile application and its configuration files
* `app/` – Contains the Android source code, activities, layouts, resources, and application configuration
* `app.py` – Contains the Flask REST API that processes health data and returns health-risk predictions
* `dataset1.ipynb` – Generates the synthetic dataset, preprocesses data, trains and evaluates the Machine Learning model
* `healthsense_synthetic_dataset.csv` – Contains the generated synthetic healthcare dataset
* `healthsense_model_final.pkl` – Stores the trained Random Forest classification model
* `scaler.pkl` – Stores the fitted StandardScaler used for feature scaling
* `label_encoder.pkl` – Stores the fitted LabelEncoder used to convert predictions into readable health-risk categories
* `requirements.txt` – Contains all required Python libraries and dependencies
* `render.yaml` – Contains application configuration settings
* `build.gradle` – Contains the Android project's build configuration and dependencies
* `gradle.properties` – Contains Gradle configuration properties
* `gradlew` – Gradle executable for Linux and macOS
* `gradlew.bat` – Gradle executable for Windows
* `settings.gradle` – Defines the modules included in the Android application
* `README.md` – Contains the project documentation

---


## 🔗 API Endpoints

### Home Endpoint

```http
GET /
```

Example response:

```json
{
  "message": "Welcome to the HealthSense API"
}
```

---

### Health Risk Prediction Endpoint

```http
POST /predict
```

The `/predict` endpoint accepts health information in JSON format and returns the predicted health risk.

### Example Request

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

### Example Response

```json
{
  "prediction": "Cardiac Risk"
}
```

---

## 🧪 Testing the API Using Postman

1. Start the Flask application.

2. Open Postman.

3. Select the `POST` request method.

4. Enter the following URL:

```text
http://127.0.0.1:5000/predict
```

5. Open:

```text
Body → raw → JSON
```

6. Enter the health information:

```json
{
  "heart_rate": 80,
  "spo2": 98,
  "sleep_hours": 7,
  "stress_level": 3,
  "body_temperature": 36.8,
  "age": 25,
  "systolic_bp": 120,
  "diastolic_bp": 80
}
```

7. Click **Send**.

Example output:

```json
{
  "prediction": "Normal"
}
```

---

## ☁️ Deployment

The Flask API can be deployed using Render.

Use the following build command:

```bash
pip install -r requirements.txt
```

Use the following start command:

```bash
gunicorn app:app
```

After deployment, the API can be accessed using:

```text
https://your-healthsense-api.onrender.com
```

The prediction endpoint will be:

```text
https://your-healthsense-api.onrender.com/predict
```

---

## 🔮 Future Enhancements

* Integrate real-time wearable health data
* Add user authentication
* Connect the application to a database
* Develop a responsive React frontend
* Add health history tracking
* Generate personalized health recommendations
* Add health trends and visualization dashboards
* Integrate an AI-powered health chatbot
* Improve the model using real-world healthcare datasets
* Deploy the complete application on a cloud platform

---

## ⚠️ Disclaimer

HealthSense API is an educational Machine Learning project and is not intended to provide medical diagnoses.

The predictions generated by the system should not be considered professional medical advice. Users should consult a qualified healthcare professional for medical concerns or emergencies.

---

## 👩‍💻 Author

**Kanika Kumar**

B.Tech Computer Science and Engineering
VIT Bhopal University

GitHub: `kanika-kumar05`

---

## ⭐ Support

If you found this project useful, consider giving the repository a ⭐.
