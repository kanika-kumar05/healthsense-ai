package com.healthsenseai.models

data class InsightCard(
    val title: String,
    val description: String,
    val sevenDayValues: List<String>
)