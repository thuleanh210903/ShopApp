package com.example.skincareshopapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skincareshopapp.R
import com.example.skincareshopapp.activity.ProductActivity
import com.example.skincareshopapp.model.CategoryProductModel
import de.hdodenhof.circleimageview.CircleImageView

class ViewPagerAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ViewPagerAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val imageUrl = images[position]
        Glide.with(holder.itemView)
            .load(imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}