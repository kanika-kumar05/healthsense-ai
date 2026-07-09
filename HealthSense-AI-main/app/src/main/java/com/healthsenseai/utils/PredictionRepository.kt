package com.healthsenseai.utils

import com.healthsenseai.network.PredictionRequest
import com.healthsenseai.network.PredictionResponse
import com.healthsenseai.network.RetrofitClient

class PredictionRepository {

    private val api = RetrofitClient.apiService

    /**
     * Sends a prediction request to the Flask API.
     * Returns Result.success with the response, or Result.failure on any error.
     */
    suspend fun predict(request: PredictionRequest): Result<PredictionResponse> {
        return try {
            val response = api.predict(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
