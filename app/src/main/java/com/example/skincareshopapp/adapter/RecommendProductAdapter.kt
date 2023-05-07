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
import com.example.skincareshopapp.activity.ProductInfoActivity
import com.example.skincareshopapp.model.Product

class RecommendProductAdapter(private  val context: Context, private val recommendList: List<Product>)
    : RecyclerView.Adapter<RecommendProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return recommendList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }


}