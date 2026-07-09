package com.healthsenseai.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healthsenseai.databinding.ItemOnboardingBinding
import com.healthsenseai.models.OnboardingPage

class OnboardingAdapter(private val items: List<OnboardingPage>) : RecyclerView.Adapter<OnboardingAdapter.VH>() {
    class VH(val binding: ItemOnboardingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.title.text = item.title
        holder.binding.subtitle.text = item.subtitle
    }

    override fun getItemCount(): Int = items.size
}