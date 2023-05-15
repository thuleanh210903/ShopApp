package com.example.skincareshopapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skincareshopapp.R
import com.example.skincareshopapp.model.IntroSlide

class SplashAdapter(private val introSlide: List<IntroSlide>) :
    RecyclerView.Adapter<SplashAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val desc:TextView = itemView.findViewById(R.id.textIntro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, parent, false)
        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val intro = introSlide[position]
        holder.desc.text = intro.desc
        Glide.with(holder.itemView)
            .load(intro.image)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return introSlide.size
    }
}