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

class ShopAdapter(private  val context: Context, private val products: MutableList<Product>)
    : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_all_shop_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nameProduct.text = product.name_product
        holder.priceProduct.text = product.price.toString()
        Glide.with(context).load("http://192.168.1.4/doancs2/public/public/admin/image/product/"+product.product_image).into(holder.imageProduct)

        holder.productInfo.setOnClickListener {

            val intent = Intent(context, ProductInfoActivity::class.java)
            intent.putExtra("price",product.price.toString())
            intent.putExtra("id_product",product.id_product.toString())
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun filter(newText: String?) {
        val tempList = ArrayList<Product>()
        if (newText != null) {
            for (item in products) {
                if (item.name_product!!.toLowerCase().contains(newText.toLowerCase())) {
                    tempList.add(item)
                }
            }
        } else {
            tempList.addAll(products)
        }
        products.clear()
        products.addAll(tempList)
        notifyDataSetChanged()

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameProduct: TextView = itemView.findViewById(R.id.nameProduct)
        val priceProduct: TextView = itemView.findViewById(R.id.priceProduct)
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val productInfo : ConstraintLayout = itemView.findViewById(R.id.productInfo)
    }
}