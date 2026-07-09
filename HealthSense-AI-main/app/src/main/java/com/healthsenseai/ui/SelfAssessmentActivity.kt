package com.healthsenseai.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.healthsenseai.databinding.ActivitySelfAssessmentBinding
import com.healthsenseai.models.SelfAssessmentData
import com.healthsenseai.viewmodel.SelfAssessmentViewModel

class SelfAssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfAssessmentBinding
    private lateinit var viewModel: SelfAssessmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Action bar with back arrow
        supportActionBar?.apply {
            title = "Self-Assessment"
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel = ViewModelProvider(this)[SelfAssessmentViewModel::class.java]

        // Load saved data and populate the form
        viewModel.load()
        viewModel.assessmentData.observe(this) { data ->
            populateForm(data)
        }

        binding.btnSave.setOnClickListener {
            saveForm()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun populateForm(data: SelfAssessmentData) {
        binding.etAge.setText(if (data.age > 0) data.age.toString() else "")
        binding.etHeight.setText(if (data.heightCm > 0f) data.heightCm.toString() else "")
        binding.etWeight.setText(if (data.weightKg > 0f) data.weightKg.toString() else "")
        binding.etSystolic.setText(if (data.systolicBP > 0) data.systolicBP.toString() else "")
        binding.etDiastolic.setText(if (data.diastolicBP > 0) data.diastolicBP.toString() else "")
        binding.switchDiabetes.isChecked = data.hasDiabetes
        binding.switchSmoking.isChecked = data.isSmoker
        binding.etConditions.setText(data.medicalConditions)
    }

    private fun saveForm() {
        // Clear prior errors
        binding.tilAge.error = null
        binding.tilHeight.error = null
        binding.tilWeight.error = null
        binding.tilSystolic.error = null
        binding.tilDiastolic.error = null

        val ageStr = binding.etAge.text.toString().trim()
        val heightStr = binding.etHeight.text.toString().trim()
        val weightStr = binding.etWeight.text.toString().trim()
        val systolicStr = binding.etSystolic.text.toString().trim()
        val diastolicStr = binding.etDiastolic.text.toString().trim()

        var hasError = false

        if (ageStr.isEmpty()) {
            binding.tilAge.error = "Age is required"
            hasError = true
        }
        if (heightStr.isEmpty()) {
            binding.tilHeight.error = "Height is required"
            hasError = true
        }
        if (weightStr.isEmpty()) {
            binding.tilWeight.error = "Weight is required"
            hasError = true
        }
        if (systolicStr.isEmpty()) {
            binding.tilSystolic.error = "Required"
            hasError = true
        }
        if (diastolicStr.isEmpty()) {
            binding.tilDiastolic.error = "Required"
            hasError = true
        }

        if (hasError) return

        val data = SelfAssessmentData(
            age = ageStr.toIntOrNull() ?: 0,
            heightCm = heightStr.toFloatOrNull() ?: 0f,
            weightKg = weightStr.toFloatOrNull() ?: 0f,
            systolicBP = systolicStr.toIntOrNull() ?: 0,
            diastolicBP = diastolicStr.toIntOrNull() ?: 0,
            hasDiabetes = binding.switchDiabetes.isChecked,
            isSmoker = binding.switchSmoking.isChecked,
            medicalConditions = binding.etConditions.text.toString().trim()
        )

        viewModel.save(data)
        Toast.makeText(this, "Assessment saved!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
