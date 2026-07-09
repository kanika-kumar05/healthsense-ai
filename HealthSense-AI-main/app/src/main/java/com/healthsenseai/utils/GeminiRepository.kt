package com.healthsenseai.utils

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.healthsenseai.BuildConfig

class GeminiRepository {

    private val TAG = "GeminiRepository"

    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    /**
     * Generates personalized preventive health suggestions using Gemini
     * based on the ML risk prediction and user health parameters.
     */
    suspend fun getSuggestions(
        riskLevel: String,
        heartRate: Int,
        spo2: Int,
        sleepHours: Float,
        stressLevel: Int,
        bodyTemp: Float,
        age: Int,
        systolicBp: Int,
        diastolicBp: Int,
        hasDiabetes: Boolean = false,
        isSmoker: Boolean = false,
        medicalConditions: String = ""
    ): Result<List<String>> {
        return try {
            Log.d(TAG, "Fetching suggestions for Risk Level: $riskLevel")
            val prompt = buildPrompt(
                riskLevel, heartRate, spo2, sleepHours, stressLevel,
                bodyTemp, age, systolicBp, diastolicBp,
                hasDiabetes, isSmoker, medicalConditions
            )

            Log.d(TAG, "Generated Prompt: $prompt")
            val response = model.generateContent(prompt)
            val text = response.text
            
            if (text == null) {
                Log.e(TAG, "Gemini response text is null")
                return Result.failure(Exception("Empty response from Gemini"))
            }

            Log.d(TAG, "Gemini Response: $text")
            val suggestions = parseSuggestions(text)
            if (suggestions.isEmpty()) {
                Log.e(TAG, "Parsed suggestions list is empty. Text was: $text")
                Result.failure(Exception("Could not parse suggestions"))
            } else {
                Log.d(TAG, "Successfully parsed ${suggestions.size} suggestions")
                Result.success(suggestions)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching suggestions: ${e.message}", e)
            Result.failure(e)
        }
    }

    private fun buildPrompt(
        riskLevel: String,
        heartRate: Int,
        spo2: Int,
        sleepHours: Float,
        stressLevel: Int,
        bodyTemp: Float,
        age: Int,
        systolicBp: Int,
        diastolicBp: Int,
        hasDiabetes: Boolean = false,
        isSmoker: Boolean = false,
        medicalConditions: String = ""
    ): String {
        val stressText = when {
            stressLevel <= 3 -> "Low"
            stressLevel <= 6 -> "Moderate"
            else -> "High"
        }

        return """
            You are a wellness assistant in a health monitoring app.
            Based on the following user health data, provide exactly 4-5 short and clear preventive health tips.
            
            User Health Data:
            - Risk Level: ${riskLevel.replace("_", " ")}
            - Heart Rate: $heartRate bpm
            - Body Temperature: $bodyTemp °F
            - SpO2: $spo2 %
            - Blood Pressure: $systolicBp / $diastolicBp mmHg
            - Sleep Hours: $sleepHours hours
            - Stress Level: $stressText ($stressLevel/10)
            - Age: $age years
            - Pre-existing Conditions: ${if (medicalConditions.isBlank()) "None reported" else medicalConditions}
            - Has Diabetes: ${if (hasDiabetes) "Yes" else "No"}
            - Is Smoker: ${if (isSmoker) "Yes" else "No"}
            
            Rules:
            - Provide 4-5 tips, numbered 1 to 5
            - Each tip should be 1-2 sentences maximum
            - Focus on actionable preventive measures
            - Do NOT diagnose any medical condition
            - Do NOT recommend medication
            - Keep the tone friendly and encouraging
            - Format: each tip on its own line, starting with the number (e.g., "1. ...")
        """.trimIndent()
    }

    /**
     * Parses suggestions from Gemini response text.
     * Handles numbered lists (1. ...), bullet points (- ...), or plain text.
     */
    private fun parseSuggestions(text: String): List<String> {
        val cleanedLines = text.lines()
            .map { it.trim() }
            .filter { it.isNotBlank() }

        // Try to match numbered items: "1. text", "1) text", etc.
        val numberedPattern = Regex("^\\d+[.):]\\s*(.+)")
        val numberedItems = cleanedLines
            .mapNotNull { numberedPattern.find(it)?.groupValues?.get(1) }

        if (numberedItems.size >= 3) return numberedItems.take(5)

        // Try to match bullet points: "- text", "* text", etc.
        val bulletPattern = Regex("^[-*•]\\s*(.+)")
        val bulletItems = cleanedLines
            .mapNotNull { bulletPattern.find(it)?.groupValues?.get(1) }

        if (bulletItems.size >= 3) return bulletItems.take(5)

        // Fallback: take the first 5 non-empty lines that aren't headers or very short
        return cleanedLines
            .filter { it.length > 5 && !it.startsWith("#") }
            .take(5)
    }
}
