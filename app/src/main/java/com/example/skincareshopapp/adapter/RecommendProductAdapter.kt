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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommend_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = recommendList[position]
        holder.nameProduct.text = product.name_product
        holder.priceProduct.text = product.price.toString()
        Glide.with(context).load("http://192.168.1.4/doancs2/public/public/admin/image/product/"+product.product_image).into(holder.imageProduct)

        holder.productInfo.setOnClickListener {

            val intent = Intent(context,ProductInfoActivity::class.java)
            intent.putExtra("price",product.price.toString())
            intent.putExtra("id_product",product.id_product.toString())
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return recommendList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameProduct: TextView = itemView.findViewById(R.id.nameProduct)
        val priceProduct: TextView = itemView.findViewById(R.id.priceProduct)
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val productInfo : ConstraintLayout = itemView.findViewById(R.id.productInfo)

    }


}