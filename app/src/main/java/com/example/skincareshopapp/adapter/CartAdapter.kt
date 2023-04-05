package com.example.skincareshopapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skincareshopapp.R
import com.example.skincareshopapp.model.Cart

class CartAdapter(private val context: Context,private val cartItems: List<Cart>):RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val nameProduct: TextView = itemView.findViewById(R.id.nameProduct)
        val priceProduct : TextView = itemView.findViewById(R.id.priceProduct)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.nameProduct.text = cartItem.productName

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}