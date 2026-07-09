package com.healthsenseai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.healthsenseai.network.PredictionRequest
import com.healthsenseai.utils.GeminiRepository
import com.healthsenseai.utils.PredictionRepository
import com.healthsenseai.utils.SelfAssessmentRepository
import kotlinx.coroutines.launch

class PredictionsViewModel(application: Application) : AndroidViewModel(application) {

    private val predictionRepo = PredictionRepository()
    private val geminiRepo = GeminiRepository()
    private val selfAssessRepo = SelfAssessmentRepository(application)

    val riskLabel = MutableLiveData<String>("--")
    val isLoading = MutableLiveData<Boolean>(false)
    val errorMessage = MutableLiveData<String?>(null)

    // Gemini suggestions
    val suggestions = MutableLiveData<List<String>>(emptyList())
    val isGeminiLoading = MutableLiveData<Boolean>(false)

    // Provide access to SensorSimulator flow for the Fragment
    val smartwatchData = com.healthsenseai.utils.SensorSimulator.sensorData

    /**
     * Runs the ML prediction using simulated smartwatch data and self-assessment data.
     */
    fun analyzeHealth() {
        val selfData = selfAssessRepo.load()
        val currentSensorData = smartwatchData.value

        val request = PredictionRequest(
            heartRate = currentSensorData.heartRate,
            spo2 = currentSensorData.spo2,
            sleepHours = currentSensorData.sleepHours,
            stressLevel = currentSensorData.stressLevel,
            bodyTemp = currentSensorData.bodyTemp,
            age = selfData.age,
            systolicBp = selfData.systolicBP,
            diastolicBp = selfData.diastolicBP
        )

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            suggestions.value = emptyList()

            val result = predictionRepo.predict(request)

            result.fold(
                onSuccess = { response ->
                    val riskText = response.riskLevel.replace("_", " ")
                    riskLabel.value = riskText
                    isLoading.value = false

                    // Now fetch Gemini suggestions
                    fetchSuggestions(
                        riskLevel = riskText,
                        heartRate = currentSensorData.heartRate,
                        spo2 = currentSensorData.spo2,
                        sleepHours = currentSensorData.sleepHours,
                        stressLevel = currentSensorData.stressLevel,
                        bodyTemp = currentSensorData.bodyTemp,
                        age = selfData.age,
                        systolicBp = selfData.systolicBP,
                        diastolicBp = selfData.diastolicBP,
                        hasDiabetes = selfData.hasDiabetes,
                        isSmoker = selfData.isSmoker,
                        medicalConditions = selfData.medicalConditions
                    )
                },
                onFailure = { e ->
                    errorMessage.value = "Could not reach server. Check your connection."
                    riskLabel.value = "--"
                    isLoading.value = false
                }
            )
        }
    }

    private fun fetchSuggestions(
        riskLevel: String,
        heartRate: Int,
        spo2: Int,
        sleepHours: Float,
        stressLevel: Int,
        bodyTemp: Float,
        age: Int,
        systolicBp: Int,
        diastolicBp: Int,
        hasDiabetes: Boolean,
        isSmoker: Boolean,
        medicalConditions: String
    ) {
        viewModelScope.launch {
            isGeminiLoading.value = true

            val result = geminiRepo.getSuggestions(
                riskLevel = riskLevel,
                heartRate = heartRate,
                spo2 = spo2,
                sleepHours = sleepHours,
                stressLevel = stressLevel,
                bodyTemp = bodyTemp,
                age = age,
                systolicBp = systolicBp,
                diastolicBp = diastolicBp,
                hasDiabetes = hasDiabetes,
                isSmoker = isSmoker,
                medicalConditions = medicalConditions
            )

            result.fold(
                onSuccess = { tips ->
                    suggestions.value = tips
                },
                onFailure = { e ->
                    suggestions.value = listOf("Error: ${e.localizedMessage ?: "Unknown error"}")
                }
            )

            isGeminiLoading.value = false
        }
    }
}