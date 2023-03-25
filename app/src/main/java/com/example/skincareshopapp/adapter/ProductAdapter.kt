package com.example.skincareshopapp.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
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
import com.example.skincareshopapp.model.Product
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.skincareshopapp.activity.ProductInfoActivity
import com.example.skincareshopapp.activity.SplashActivity

class ProductAdapter(private  val context: Context, private val products: List<Product>)
    :RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nameProduct.text = product.name_product
        holder.priceProduct.text = product.price.toString()
        Glide.with(context).load("http://192.168.1.11/doancs2/public/public/admin/image/product/"+product.product_image).into(holder.imageProduct)

        holder.productInfo.setOnClickListener {

            val intent = Intent(context,ProductInfoActivity::class.java)
            intent.putExtra("id_product",product.id_product.toString())
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameProduct: TextView = itemView.findViewById(R.id.nameProduct)
        val priceProduct: TextView = itemView.findViewById(R.id.priceProduct)
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val productInfo : ConstraintLayout = itemView.findViewById(R.id.productInfo)
    }


}