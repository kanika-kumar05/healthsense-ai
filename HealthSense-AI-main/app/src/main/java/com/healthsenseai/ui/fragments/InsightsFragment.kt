package com.healthsenseai.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthsenseai.adapters.InsightsAdapter
import com.healthsenseai.databinding.FragmentInsightsBinding
import com.healthsenseai.viewmodel.InsightsViewModel

class InsightsFragment : Fragment() {
    private var _binding: FragmentInsightsBinding? = null
    private val binding get() = _binding!!
    private val viewModel = InsightsViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInsightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = InsightsAdapter(viewModel.cards)
        binding.insightsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.insightsRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}