import os
import pickle
import numpy as np
from flask import Flask, request, jsonify

app = Flask(__name__)

# ---------------------------------------------------------------------------
# Load the trained ML model once at startup
# ---------------------------------------------------------------------------
MODEL_PATH = os.path.join(os.path.dirname(__file__), "healthsense_model.pkl")

model = None
model_error = None

try:
    with open(MODEL_PATH, "rb") as f:
        model = pickle.load(f)
    print(f"[HealthSense] Model loaded successfully from {MODEL_PATH}")
except FileNotFoundError:
    model_error = (
        "healthsense_model.pkl not found. "
        "Place the trained model file in the same directory as app.py."
    )
    print(f"[HealthSense] WARNING: {model_error}")
except Exception as e:
    model_error = str(e)
    print(f"[HealthSense] ERROR loading model: {model_error}")


# ---------------------------------------------------------------------------
# Feature order must match training data
# ---------------------------------------------------------------------------
FEATURE_KEYS = [
    "age",
    "heart_rate",
    "temperature",
    "spo2",
    "systolic_bp",
    "diastolic_bp",
    "diabetes",
    "smoking",
]

RISK_LABELS = {0: "Low Risk", 1: "Medium Risk", 2: "High Risk"}


# ---------------------------------------------------------------------------
# Health check
# ---------------------------------------------------------------------------
@app.route("/", methods=["GET"])
def health_check():
    status = "ready" if model is not None else "model_not_loaded"
    return jsonify({"status": status, "service": "HealthSense ML API"})


# ---------------------------------------------------------------------------
# Prediction endpoint
# ---------------------------------------------------------------------------
@app.route("/predict", methods=["POST"])
def predict():
    # Model availability guard
    if model is None:
        return jsonify({"error": model_error or "Model not loaded"}), 503

    # Parse JSON body
    data = request.get_json(silent=True)
    if data is None:
        return jsonify({"error": "Request body must be valid JSON"}), 400

    # Validate all features are present
    missing = [k for k in FEATURE_KEYS if k not in data]
    if missing:
        return jsonify({"error": f"Missing fields: {missing}"}), 400

    # Build numpy feature array
    try:
        features = np.array([[float(data[k]) for k in FEATURE_KEYS]])
    except (ValueError, TypeError) as e:
        return jsonify({"error": f"Invalid feature value: {e}"}), 400

    # Run prediction
    try:
        prediction = int(model.predict(features)[0])
    except Exception as e:
        return jsonify({"error": f"Prediction failed: {e}"}), 500

    return jsonify({
        "risk_level": prediction,
        "risk_label": RISK_LABELS.get(prediction, "Unknown")
    })


# ---------------------------------------------------------------------------
# Entry point (local dev)
# ---------------------------------------------------------------------------
if __name__ == "__main__":
    port = int(os.environ.get("PORT", 5000))
    app.run(host="0.0.0.0", port=port, debug=False)
