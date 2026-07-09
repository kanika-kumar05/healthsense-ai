package com.healthsenseai.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.healthsenseai.databinding.FragmentHomeBinding
import com.healthsenseai.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.predictedRisk.text = viewModel.predictedRisk

        // Start green dot pulse animation
        val pulseAnimation = ObjectAnimator.ofPropertyValuesHolder(
            binding.dotConnected,
            PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0.4f, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.2f, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.2f, 1f)
        ).apply {
            duration = 1500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
        }
        pulseAnimation.start()

        // Observe flows
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { 
                    viewModel.heartRate.collect { 
                        binding.heartRateValue.text = it 
                        
                        // Live Sync Animation
                        binding.tvLastSync.text = "Last Sync: Just now"
                        binding.tvLastSync.alpha = 0f
                        binding.tvLastSync.animate().alpha(1f).setDuration(500).start()
                    } 
                }
                launch { viewModel.spo2.collect { binding.spo2Value.text = it } }
                launch { viewModel.stress.collect { binding.stressValue.text = it } }
                launch { viewModel.sleep.collect { binding.sleepValue.text = it } }
                launch { viewModel.breathRate.collect { binding.breathRateValue.text = it } }
            }
        }

        binding.btnViewInsights.setOnClickListener {
            activity?.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(com.healthsenseai.R.id.bottomNav)?.selectedItemId = com.healthsenseai.R.id.nav_insights
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}