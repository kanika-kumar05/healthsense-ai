package com.healthsenseai.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healthsenseai.databinding.ItemSuggestionBinding

class SuggestionsAdapter : RecyclerView.Adapter<SuggestionsAdapter.VH>() {

    private var items: List<String> = emptyList()

    fun submitList(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }

    class VH(val binding: ItemSuggestionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemSuggestionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvSuggestionNumber.text = "${position + 1}"
        holder.binding.tvSuggestionText.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}
