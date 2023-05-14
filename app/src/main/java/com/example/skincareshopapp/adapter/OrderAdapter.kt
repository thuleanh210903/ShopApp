package com.example.skincareshopapp.adapter
import com.example.skincareshopapp.model.Orders
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

class OrderAdapter(private  val context: Context, private val orders: List<Orders>)
    :RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.order_total.text = order.order_total.toString()
        holder.order_date.text = order.order_date.toString()
        holder.order_status.text = order.order_status.toString()

    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val order_total: TextView = itemView.findViewById(R.id.tvTotalUser)
        val order_status: TextView = itemView.findViewById(R.id.tvOrderStatus)
        val order_date: TextView = itemView.findViewById(R.id.tvOrderDateUser)

    }


}