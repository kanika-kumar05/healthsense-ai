package com.healthsenseai.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    val userName = MutableLiveData<String>("Kuldeep")
    val smartwatchStatus = MutableLiveData<String>("NoiseFit — Connected")
    val healthScore = MutableLiveData<String>("92/100")
    val accountCreated = MutableLiveData<String>("Member since Jan 2026")
    
    fun updateProfile(newName: String, newSmartwatchStatus: String) {
        userName.value = newName
        smartwatchStatus.value = newSmartwatchStatus
    }
}