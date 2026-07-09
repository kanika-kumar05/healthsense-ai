package com.healthsenseai.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.healthsenseai.R
import com.healthsenseai.databinding.ItemInsightCardBinding
import com.healthsenseai.models.InsightCard

class InsightsAdapter(private val items: List<InsightCard>) : RecyclerView.Adapter<InsightsAdapter.VH>() {
    class VH(val binding: ItemInsightCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemInsightCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        val context = holder.binding.root.context
        
        holder.binding.title.text = item.title
        holder.binding.description.text = item.description
        holder.binding.values.text = "Values: ${item.sevenDayValues.joinToString(separator = "  ")}"
        
        // Set appropriate icon and color based on metric type
        val (iconRes, iconTint) = when {
            item.title.contains("Heart", ignoreCase = true) -> 
                R.drawable.ic_heart to context.getColor(R.color.colorError)
            item.title.contains("SpO", ignoreCase = true) || item.title.contains("Oxygen", ignoreCase = true) -> 
                R.drawable.ic_drop to context.getColor(R.color.colorError)
            item.title.contains("Sleep", ignoreCase = true) -> 
                R.drawable.ic_moon to context.getColor(R.color.colorTertiary)
            item.title.contains("Stress", ignoreCase = true) -> 
                R.drawable.ic_stress to context.getColor(R.color.colorWarning)
            item.title.contains("Breath", ignoreCase = true) -> 
                R.drawable.ic_lungs to context.getColor(R.color.colorPrimary)
            else -> 
                R.drawable.ic_heart to context.getColor(R.color.colorPrimary)
        }
        
        holder.binding.icon.setImageResource(iconRes)
        holder.binding.icon.setColorFilter(iconTint)
        
        // Create mini bar chart
        createBarChart(holder.binding.chartContainer, item.sevenDayValues, iconTint)
    }

    private fun createBarChart(container: LinearLayout, values: List<String>, color: Int) {
        container.removeAllViews()
        
        // Parse values to numbers (handle both numeric and text values)
        val numericValues = values.mapNotNull { value ->
            value.replace(Regex("[^0-9.]"), "").toDoubleOrNull()
        }
        
        if (numericValues.isEmpty()) {
            // For text values (like "Low", "Medium"), create uniform bars
            val uniformHeight = 60
            values.forEach { _ ->
                val bar = View(container.context)
                val barWidth = (container.context.resources.displayMetrics.density * 8).toInt()
                val params = LinearLayout.LayoutParams(0, uniformHeight)
                params.weight = 1f
                params.marginStart = (container.context.resources.displayMetrics.density * 2).toInt()
                params.marginEnd = (container.context.resources.displayMetrics.density * 2).toInt()
                bar.layoutParams = params
                bar.setBackgroundColor(color)
                bar.alpha = 0.7f
                container.addView(bar)
            }
            return
        }
        
        // Find max value for scaling
        val maxValue = numericValues.maxOrNull() ?: 1.0
        val minValue = numericValues.minOrNull() ?: 0.0
        val range = (maxValue - minValue).coerceAtLeast(1.0)
        
        // Create bars
        numericValues.forEach { value ->
            val bar = View(container.context)
            val normalizedHeight = ((value - minValue) / range)
            val barHeight = (normalizedHeight * 80 + 20).toInt() // Min 20dp, max 100dp
            
            val params = LinearLayout.LayoutParams(0, barHeight)
            params.weight = 1f
            params.marginStart = (container.context.resources.displayMetrics.density * 2).toInt()
            params.marginEnd = (container.context.resources.displayMetrics.density * 2).toInt()
            bar.layoutParams = params
            bar.setBackgroundColor(color)
            bar.alpha = 0.8f
            
            container.addView(bar)
        }
    }

    override fun getItemCount(): Int = items.size
}