package com.healthsenseai.network

import com.google.gson.annotations.SerializedName

data class PredictionRequest(
    @SerializedName("heart_rate") val heartRate: Int,
    @SerializedName("spo2") val spo2: Int,
    @SerializedName("sleep_hours") val sleepHours: Float,
    @SerializedName("stress_level") val stressLevel: Int,
    @SerializedName("body_temp") val bodyTemp: Float,
    @SerializedName("age") val age: Int,
    @SerializedName("systolic_bp") val systolicBp: Int,
    @SerializedName("diastolic_bp") val diastolicBp: Int
)
