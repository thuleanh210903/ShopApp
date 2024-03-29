package com.example.skincareshopapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skincareshopapp.R
import com.example.skincareshopapp.activity.ProductActivity
import com.example.skincareshopapp.model.CategoryProductModel
import de.hdodenhof.circleimageview.CircleImageView

class CategoryProductAdapter(private val context: Context, private val listCategory: List<CategoryProductModel>)
    : RecyclerView.Adapter<CategoryProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_category_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryProductAdapter.ViewHolder, position: Int) {
        val category = listCategory[position]
        holder.nameCategory.text = category.name_category_product
        Glide.with(context).load("http://192.168.1.11/doancs2/public/public/admin/image/cate/"+category.image).into(holder.image)

        holder.productByCate.setOnClickListener {
            val intent = Intent(context,ProductActivity::class.java)
            intent.putExtra("id_category_product",category.id_category_product.toString())
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameCategory: TextView = itemView.findViewById(R.id.nameCategory)
        val image: CircleImageView = itemView.findViewById(R.id.image)
        val productByCate: ConstraintLayout = itemView.findViewById(R.id.productByCate)

    }

}