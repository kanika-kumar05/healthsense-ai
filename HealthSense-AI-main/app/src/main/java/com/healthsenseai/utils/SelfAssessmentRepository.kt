package com.healthsenseai.utils

import android.content.Context
import com.healthsenseai.models.SelfAssessmentData

class SelfAssessmentRepository(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(data: SelfAssessmentData) {
        prefs.edit().apply {
            putInt(KEY_AGE, data.age)
            putFloat(KEY_HEIGHT, data.heightCm)
            putFloat(KEY_WEIGHT, data.weightKg)
            putInt(KEY_SYSTOLIC, data.systolicBP)
            putInt(KEY_DIASTOLIC, data.diastolicBP)
            putBoolean(KEY_DIABETES, data.hasDiabetes)
            putBoolean(KEY_SMOKER, data.isSmoker)
            putString(KEY_CONDITIONS, data.medicalConditions)
        }.apply()
    }

    fun load(): SelfAssessmentData {
        return SelfAssessmentData(
            age = prefs.getInt(KEY_AGE, 0),
            heightCm = prefs.getFloat(KEY_HEIGHT, 0f),
            weightKg = prefs.getFloat(KEY_WEIGHT, 0f),
            systolicBP = prefs.getInt(KEY_SYSTOLIC, 0),
            diastolicBP = prefs.getInt(KEY_DIASTOLIC, 0),
            hasDiabetes = prefs.getBoolean(KEY_DIABETES, false),
            isSmoker = prefs.getBoolean(KEY_SMOKER, false),
            medicalConditions = prefs.getString(KEY_CONDITIONS, "") ?: ""
        )
    }

    companion object {
        private const val PREFS_NAME = "self_assessment_prefs"
        private const val KEY_AGE = "age"
        private const val KEY_HEIGHT = "height"
        private const val KEY_WEIGHT = "weight"
        private const val KEY_SYSTOLIC = "systolic_bp"
        private const val KEY_DIASTOLIC = "diastolic_bp"
        private const val KEY_DIABETES = "has_diabetes"
        private const val KEY_SMOKER = "is_smoker"
        private const val KEY_CONDITIONS = "medical_conditions"
    }
}
