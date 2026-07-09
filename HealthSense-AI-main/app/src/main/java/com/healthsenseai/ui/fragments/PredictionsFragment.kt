package com.healthsenseai.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthsenseai.adapters.SuggestionsAdapter
import com.healthsenseai.databinding.FragmentPredictionsBinding
import com.healthsenseai.viewmodel.PredictionsViewModel
import kotlinx.coroutines.launch

class PredictionsFragment : Fragment() {

    private var _binding: FragmentPredictionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PredictionsViewModel
    private val suggestionsAdapter = SuggestionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPredictionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[PredictionsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // -- Setup RecyclerView for suggestions --
        binding.rvSuggestions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = suggestionsAdapter
        }

        // -- Observe risk label --
        viewModel.riskLabel.observe(viewLifecycleOwner) { label ->
            binding.tvRiskLabel.text = label

            val (bgColor, descText) = when {
                label == "--" -> Pair("#F5F5F5", "Tap 'Analyze Health' to get your risk assessment.")
                label.contains("Low", ignoreCase = true) || label.contains("Normal", ignoreCase = true) ->
                    Pair("#E8F5E9", "Your health indicators look good. Keep it up! ✅")
                label.contains("High", ignoreCase = true) || label.contains("Critical", ignoreCase = true) ->
                    Pair("#FFEBEE", "High-risk indicators detected. Please seek medical advice. 🚨")
                else ->
                    Pair("#FFF8E1", "Some parameters need attention. Consider consulting a doctor. ⚠️")
            }
            binding.cardRiskBadge.setCardBackgroundColor(Color.parseColor(bgColor))
            binding.tvRiskDescription.text = descText
        }

        // -- Observe ML loading state --
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.btnAnalyzeHealth.isEnabled = !loading
            binding.btnAnalyzeHealth.text = if (loading) "Analyzing..." else "Analyze Health"
        }

        // -- Observe error --
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                binding.cardError.visibility = View.VISIBLE
                binding.tvError.text = "⚠️  $error"
            } else {
                binding.cardError.visibility = View.GONE
            }
        }

        // -- Observe Gemini loading state --
        viewModel.isGeminiLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressGemini.visibility = if (loading) View.VISIBLE else View.GONE
            if (loading) {
                binding.layoutSuggestions.visibility = View.VISIBLE
            }
        }

        // -- Observe Gemini suggestions --
        viewModel.suggestions.observe(viewLifecycleOwner) { tips ->
            if (tips.isNotEmpty()) {
                binding.layoutSuggestions.visibility = View.VISIBLE
                suggestionsAdapter.submitList(tips)
            } else {
                binding.layoutSuggestions.visibility = View.GONE
            }
        }

        // -- Observe Smartwatch Data --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.smartwatchData.collect { data ->
                    binding.tvSrcHeartRate.text = "${data.heartRate} bpm"
                    binding.tvSrcSpo2.text = "${data.spo2} %"
                }
            }
        }

        // -- Analyze button --
        binding.btnAnalyzeHealth.setOnClickListener {
            viewModel.analyzeHealth()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}