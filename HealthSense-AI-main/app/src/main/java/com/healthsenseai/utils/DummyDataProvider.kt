package com.healthsenseai.utils

import com.healthsenseai.models.InsightCard

object DummyDataProvider {
    fun insightCards(): List<InsightCard> = listOf(
        InsightCard(
            title = "Heart Rate Trend",
            description = "Average daily heart rate over 7 days",
            sevenDayValues = listOf("72", "75", "71", "78", "74", "73", "76")
        ),
        InsightCard(
            title = "SpO₂ Levels",
            description = "Daily oxygen saturation percentage",
            sevenDayValues = listOf("98", "97", "98", "96", "97", "98", "97")
        ),
        InsightCard(
            title = "Sleep Pattern",
            description = "Hours of sleep each night",
            sevenDayValues = listOf("7.2", "6.8", "7.5", "7.0", "6.5", "7.8", "7.3")
        ),
        InsightCard(
            title = "Stress Level",
            description = "Daily stress measurements",
            sevenDayValues = listOf("Low", "Medium", "Low", "Low", "Medium", "Low", "Low")
        ),
        InsightCard(
            title = "Breath Rate",
            description = "Average breaths per minute",
            sevenDayValues = listOf("16", "17", "15", "16", "18", "16", "15")
        ),
        InsightCard(
            title = "Activity Level",
            description = "Daily step count (in thousands)",
            sevenDayValues = listOf("8.5", "9.2", "7.8", "10.1", "8.9", "9.5", "8.2")
        )
    )
}