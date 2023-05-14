package com.example.skincareshopapp.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import com.example.skincareshopapp.utilities.Constants.url
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.discount_dialog.*
import kotlinx.android.synthetic.main.order_dialog_layout.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class OrderActivity : AppCompatActivity() {
    private lateinit var loginSession:LoginPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val id_order = intent.getStringExtra("id_order")
        val id_shipping = intent.getStringExtra("id_shipping")
        val order_total = intent.getStringExtra("order_total")
        displayShipping(id_shipping)
        displayOrder(id_order)
        btnConfirm.setOnClickListener {
            confirm()
        }
    }


    private fun confirm() {
        val dialog:Dialog = Dialog(this)
        dialog.setContentView(R.layout.order_dialog_layout)
        dialog.show()
        val btnBack = dialog.findViewById<Button>(R.id.btnDialog)
        btnBack.setOnClickListener {
            deleteCart()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun deleteCart() {
        loginSession = LoginPref(this)
        var user : HashMap<String,String> = loginSession.getUserDetail()
        var email = user.get(LoginPref.USER_EMAIL)

        val urlDeleteCart:String = "${Constants.url}delete_all_cart.php?email="+email
        val queueDelete:RequestQueue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(
            Request.Method.GET, urlDeleteCart,
            Response.Listener<String> { response ->
            },
            Response.ErrorListener { error ->
                println(error)
            }
        ) {}
        queueDelete.add(stringRequest)
    }

    private fun displayShipping(idShipping: String?) {
        val urlDisplayShipping:String = "${Constants.url}display_shipping.php?id_shipping="+idShipping
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
                    id_order = objectOrder.getString("id_order")
                    order_total= objectOrder.getString("order_total")
                    order_status = objectOrder.getString("order_status")
                    order_date = objectOrder.getString("order_date")
                    id_shipping = objectOrder.getString("id_shipping")
                    id_coupon = objectOrder.getString("id_coupon")
                    discount.setOnClickListener {
                        discountApply(id_order.toInt())
                    }


                }
            },{
                    error ->
                println(error.message)
            }
        )
        queueOrder.add(request)

    }

    private fun discountApply(idOrder: Int) {
        val dialog:Dialog= Dialog(this)
        dialog.setContentView(R.layout.discount_dialog)
        dialog.show()
        val etDiscountCode = dialog.findViewById<EditText>(R.id.etDiscountCode)
        val btnApply = dialog.findViewById<Button>(R.id.btnApply)
        btnApply.setOnClickListener {
            val coupon_code = etDiscountCode.text.toString()
            val order_total = intent.getStringExtra("order_total")
            val discountQueue: RequestQueue
            var coupon:Double = 0.0
            val urlDiscount: String = "${Constants.url}check_coupon.php?coupon_code=" + coupon_code
            discountQueue = Volley.newRequestQueue(this)
            val request = StringRequest(
                Request.Method.GET, urlDiscount, { response ->
                    val jsonArray: JSONArray = JSONArray(response)
                    var coupon_number: String=""
                    for (discount in 0..jsonArray.length() - 1) {
                        var objectDiscount: JSONObject = jsonArray.getJSONObject(discount)
                        coupon_number = objectDiscount.getString("coupon_number")
                        coupon = coupon_number.toDouble()
                       val order_discount = order_total!!.toDouble()-coupon
                        updateOrderTotal(idOrder,order_discount)
                    }
                },
                { error ->
                    println(error.message)
                })
            discountQueue.add(request)
        }



    }

    private fun updateOrderTotal(idOrder: Int, orderDiscount: Double) {
        val urlUpdateOrder = "${Constants.url}update_order_total.php"
        val queue = Volley.newRequestQueue(this)
        val jsonObject = JSONObject()
        jsonObject.put("id_order", idOrder)
        jsonObject.put("order_total", orderDiscount)

        val stringRequest = object : StringRequest(
            Method.POST, urlUpdateOrder,
            { response ->
                // Handle the response from the server
                try {
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")
                    val message = jsonObject.getString("message")
                    if (status == "success") {
                        // Quantity updated successfully
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()

                    } else {
                        // Failed to update quantity
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    println(e)
                }
            },
            { error ->
                // Handle the error
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
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


}