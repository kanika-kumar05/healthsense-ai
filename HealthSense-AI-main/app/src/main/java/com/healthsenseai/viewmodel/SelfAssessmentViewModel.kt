package com.healthsenseai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthsenseai.models.SelfAssessmentData
import com.healthsenseai.utils.SelfAssessmentRepository

class SelfAssessmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SelfAssessmentRepository(application)

    val assessmentData = MutableLiveData<SelfAssessmentData>()

    fun load() {
        assessmentData.value = repository.load()
    }

    fun save(data: SelfAssessmentData) {
        repository.save(data)
        assessmentData.value = data
    }
}
