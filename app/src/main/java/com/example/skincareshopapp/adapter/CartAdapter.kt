package com.example.skincareshopapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.skincareshopapp.R
import com.example.skincareshopapp.activity.CartActivity
import com.example.skincareshopapp.model.ProductCartInfo
import com.example.skincareshopapp.utilities.Constants
import org.json.JSONException
import org.json.JSONObject

class CartAdapter(private val context: Context,private val cartItems: List<ProductCartInfo>, private val cartActivity: CartActivity):RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val nameProductCart: TextView = itemView.findViewById(R.id.nameProductCart)
        val priceProductCart : TextView = itemView.findViewById(R.id.priceProductCart)
        val imageProductCart : ImageView = itemView.findViewById(R.id.imageProductCart)
        val cartItem : ConstraintLayout = itemView.findViewById(R.id.cartItem)
        val quantity : TextView = itemView.findViewById(R.id.tvQuantity)
        val btnIncrease: Button = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease : Button = itemView.findViewById(R.id.btnDecrease)
//        val tvSubTotal : TextView = itemView.findViewById(R.id.tvSubtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.nameProductCart.text = cartItem.nameProductCart
        holder.priceProductCart.text = cartItem.priceProductCart.toString()
        Glide.with(context).load("http://192.168.1.4/doancs2/public/public/admin/image/product/"+cartItem.imageProductCart).into(holder.imageProductCart)
        holder.quantity.text = cartItem.quantity.toString()
        var newQuantity = cartItem.quantity
//        holder.tvSubTotal.text = sum

        holder.btnIncrease.setOnClickListener {
            var newQuantity = cartItem.quantity.inc()
            holder.quantity.text = newQuantity.toString()
            updateCart(cartItem.idCart.toString(),newQuantity.toString())
            priceByQuantity(newQuantity,cartItem.priceProductCart)

        }
        holder.btnDecrease.setOnClickListener {

            if(cartItem.quantity>0){
                val newQuantity = cartItem.quantity.dec()
                holder.quantity.text = newQuantity.toString()
                updateCart(cartItem.idCart.toString(),newQuantity.toString())
                priceByQuantity(newQuantity,cartItem.priceProductCart)



            }
            if(cartItem.quantity==0){
                val newQuantity = 0
                holder.quantity.text = newQuantity.toString()
                deleteCart(cartItem.idCart.toString())
            }

        }
        priceByQuantity(newQuantity,cartItem.priceProductCart)


    }




    private fun deleteCart(idCart: String) {
        val urlDeleteCart = "${Constants.url}delete_cart.php"
        val deleteQueue  = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.DELETE,
            urlDeleteCart,
            Response.Listener<String> { response ->
                // Handle successful response, if needed
            },
            Response.ErrorListener { error ->
                // Handle error, if needed
            }
        )

        deleteQueue.add(stringRequest)
    }

    private fun updateCart(idCart: String, newQuantity: String) {

        val urlUpdateCart = "${Constants.url}update_quantity.php"
        val queue = Volley.newRequestQueue(context)
        val jsonObject = JSONObject()
        jsonObject.put("id_cart", idCart)
        jsonObject.put("quantity", newQuantity)

        val stringRequest = object : StringRequest(
            Method.POST, urlUpdateCart,
            { response ->
                // Handle the response from the server
                try {
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")
                    val message = jsonObject.getString("message")
                    if (status == "success") {
                        // Quantity updated successfully
                        Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()

                    } else {
                        // Failed to update quantity
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    println(e)
                }
            },
            { error ->
                // Handle the error
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }) {

            // Pass the parameters in the request body
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return jsonObject.toString().toByteArray(Charsets.UTF_8)
            }
        }
        queue.add(stringRequest)
    }
    private fun priceByQuantity(newQuantity: Int, priceProductCart: Double) {
        val priceByQuantity:Double = newQuantity * priceProductCart
        var sum:Int = 0
        for (i in 0 until priceByQuantity.toInt()){
            sum = sum + priceByQuantity.toInt()
        }

    }



    override fun getItemCount(): Int {
        return cartItems.size
    }
}