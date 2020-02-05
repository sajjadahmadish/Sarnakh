package project.ui.missionList.cardhelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.sinapp.sarnakh.R

class SliderAdapter(
    private val content: IntArray,
    private val count: Int,
    private val listener: View.OnClickListener?
) : RecyclerView.Adapter<SliderCard>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderCard {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_slider_card, parent, false)
        if (listener != null) {
            view.setOnClickListener { v -> listener.onClick(v) }
        }
        return SliderCard(view)
    }

    override fun onBindViewHolder(holder: SliderCard, position: Int) {
        holder.setContent(content[position % content.size])
    }

    override fun onViewRecycled(holder: SliderCard) {
        holder.clearContent()
    }

    override fun getItemCount(): Int {
        return count
    }

}