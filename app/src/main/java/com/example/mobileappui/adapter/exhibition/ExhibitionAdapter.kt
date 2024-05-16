package com.example.mobileappui.presentation.exhibition

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileappui.R
import com.example.mobileappui.dtos.exhibition.ExhibitionDto


class ExhibitionAdapter(private val items: List<ExhibitionDto>, private val onItemClick: (ExhibitionDto) -> Unit) :
    RecyclerView.Adapter<ExhibitionAdapter.ExhibitionViewHolder>() {

    class ExhibitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val titleView: TextView = itemView.findViewById(R.id.name)

        fun bind(exhibition: ExhibitionDto, onItemClick: (ExhibitionDto) -> Unit) {
            titleView.text = exhibition.name
            Log.d("ExhibitionAdapter", titleView.text as String)
            Glide.with(itemView.context).load(exhibition.image).into(imageView)
            itemView.setOnClickListener { onItemClick(exhibition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_exhibition, parent, false)
        return ExhibitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExhibitionViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount() = items.size
}
