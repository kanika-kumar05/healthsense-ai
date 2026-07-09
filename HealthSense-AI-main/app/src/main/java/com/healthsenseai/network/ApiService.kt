package com.healthsenseai.network

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/predict")
    suspend fun predict(@Body request: PredictionRequest): PredictionResponse
}
