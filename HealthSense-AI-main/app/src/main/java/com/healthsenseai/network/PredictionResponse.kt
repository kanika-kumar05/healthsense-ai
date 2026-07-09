package com.healthsenseai.network

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("risk_category") val riskLevel: String
)
