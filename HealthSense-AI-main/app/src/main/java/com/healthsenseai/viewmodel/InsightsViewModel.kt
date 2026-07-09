package com.healthsenseai.viewmodel

import com.healthsenseai.models.InsightCard
import com.healthsenseai.utils.DummyDataProvider

class InsightsViewModel {
    val cards: List<InsightCard> = DummyDataProvider.insightCards()
}