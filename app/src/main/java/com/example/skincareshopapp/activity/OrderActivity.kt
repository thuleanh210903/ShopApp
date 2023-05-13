package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_order.*
import org.json.JSONArray
import org.json.JSONObject

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val id_order = intent.getStringExtra("id_order")
        val id_shipping = intent.getStringExtra("id_shipping")
        val urlDisplayOrder:String="${Constants.url}display_order.php?id_order="+id_order
        displayShipping(id_shipping)

        displayOrder(id_order)


        // display shipping






    }

    private fun displayShipping(idShipping: String?) {
        val urlDisplayShipping:String = "${Constants.url}display_shipping?id_shipping"+idShipping
        val queue:RequestQueue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlDisplayShipping, { response ->
                var jsonArray:JSONArray = JSONArray(response)
                var id_shipping:String=""
                var shipping_name:String=""
                var shipping_address:String=""
                var shipping_phone:String=""
                var shipping_note:String=""
                for(shipping in 0..jsonArray.length()-1){
                    var objectShipping:JSONObject = jsonArray.getJSONObject(shipping)
                    id_shipping = objectShipping.getString("id_shipping")
                    shipping_name = objectShipping.getString("shipping_name")
                    shipping_address = objectShipping.getString("shipping_address")
                    shipping_phone = objectShipping.getString("shipping_phone")
                    shipping_note = objectShipping.getString("shipping_note")
                    tvAddress.text = shipping_address
                    tvName.text = shipping_name
                    tvPhone.text = shipping_phone

                }
            },{
                    error ->
                println(error.message)
            }
        )
        queue.add(request)

    }

    private fun displayOrder(idOrder: String?) {
        val urlDisplayOrder:String="${Constants.url}display_order.php?id_order="+idOrder
        val queueOrder:RequestQueue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlDisplayOrder, { response ->
                var jsonArray:JSONArray = JSONArray(response)
                var id_order:String=""
                var order_total:String=""
                var order_status:String=""
                var order_date:String=""
                var id_shipping:String=""
                var id_coupon:String=""
                for(order in 0..jsonArray.length()-1){
                    var objectOrder:JSONObject = jsonArray.getJSONObject(order)
                    id_order = objectOrder.getString("id_shipping")
                    order_total= objectOrder.getString("order_total")
                    order_status = objectOrder.getString("order_status")
                    order_date = objectOrder.getString("order_date")
                    id_shipping = objectOrder.getString("id_shipping")
                    id_coupon = objectOrder.getString("id_coupon")


                }
            },{
                    error ->
                println(error.message)
            }
        )
        queueOrder.add(request)

    }
}