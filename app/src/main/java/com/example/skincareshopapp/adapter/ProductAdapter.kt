package com.example.skincareshopapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skincareshopapp.R
import com.example.skincareshopapp.model.Product

class ProductAdapter(private val context: Context,private val products:ArrayList<Product>)
    :RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_items,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val urlGetImage:String = "http://192.168.1.11/android/get_productImage.php"
        val product = products[position]
        holder.nameProduct.text = product.name_product
        holder.priceProduct.text = product.price.toString()
//        product.product_image = Glide.with(context).load(urlGetImage).into(holder.productImage).toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val nameProduct: TextView = itemView.findViewById(R.id.nameProduct)
        val priceProduct: TextView = itemView.findViewById(R.id.priceProduct)
//        val productImage:ImageView = itemView.findViewById(R.id.productImage)


    }

}