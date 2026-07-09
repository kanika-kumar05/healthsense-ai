package com.healthsenseai.models

data class SelfAssessmentData(
    val age: Int = 0,
    val heightCm: Float = 0f,
    val weightKg: Float = 0f,
    val systolicBP: Int = 0,
    val diastolicBP: Int = 0,
    val hasDiabetes: Boolean = false,
    val isSmoker: Boolean = false,
    val medicalConditions: String = ""
)
