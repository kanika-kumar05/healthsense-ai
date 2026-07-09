package com.healthsenseai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsenseai.utils.SensorSimulator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel : ViewModel() {

    // Keep predictedRisk static or fetch from another source if needed
    val predictedRisk = "Predicted Risk: Normal"

    val heartRate = SensorSimulator.sensorData.map { "${it.heartRate} bpm" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "78 bpm")

    val spo2 = SensorSimulator.sensorData.map { "${it.spo2}%" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "97%")

    val stress = SensorSimulator.sensorData.map { "Level ${it.stressLevel}" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Low")

    val sleep = SensorSimulator.sensorData.map { "${it.sleepHours} hours" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "7.2 hours")

    val breathRate = SensorSimulator.sensorData.map { "${(it.heartRate / 4.5).toInt()}/min" } // simple pseudo-calc
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "16/min")
}