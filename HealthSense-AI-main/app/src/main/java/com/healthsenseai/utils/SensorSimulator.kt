package com.healthsenseai.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class SmartwatchData(
    val heartRate: Int = 72,
    val spo2: Int = 98,
    val sleepHours: Float = 7f,
    val stressLevel: Int = 3,
    val bodyTemp: Float = 36.8f,
    val activityLevel: Int = 5
)

object SensorSimulator {
    private val _sensorData = MutableStateFlow(generateRandomData())
    val sensorData: StateFlow<SmartwatchData> = _sensorData

    init {
        startSimulation()
    }

    private fun startSimulation() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(10000) // Update every 10 seconds
                _sensorData.value = generateRandomData()
            }
        }
    }

    private fun generateRandomData(): SmartwatchData {
        return SmartwatchData(
            heartRate = generateHeartRate(),
            spo2 = generateSpO2(),
            sleepHours = generateSleepHours(),
            stressLevel = generateStressLevel(),
            bodyTemp = generateBodyTemp(),
            activityLevel = generateActivityLevel()
        )
    }

    private fun generateHeartRate(): Int {
        return Random.nextInt(65, 86) // Realistic resting HR
    }

    private fun generateSpO2(): Int {
        return Random.nextInt(96, 100) // 96 to 99
    }

    private fun generateSleepHours(): Float {
        // 6.5 to 8.5
        return kotlin.math.round((Random.nextFloat() * 2f + 6.5f) * 10f) / 10f
    }

    private fun generateStressLevel(): Int {
        return Random.nextInt(1, 11) // 1 to 10
    }

    private fun generateBodyTemp(): Float {
        // Normal human body temp is ~36.5 to 37.5 Celsius
        return kotlin.math.round((Random.nextFloat() * 1.0f + 36.5f) * 10f) / 10f
    }

    private fun generateActivityLevel(): Int {
        return Random.nextInt(1, 11) // 1 to 10
    }

    // Age and BP come from SelfAssessment, not smartwatch usually
    // But as requested, we can keep them out of here since they are collected manually
    // The prompt says: "Combine the simulated smartwatch data with existing self-assessment data (age, weight, etc.)"
}
