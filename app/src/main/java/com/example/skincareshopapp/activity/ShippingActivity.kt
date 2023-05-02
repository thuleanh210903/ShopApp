package com.example.skincareshopapp.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi

import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_shipping.*
import kotlinx.android.synthetic.main.activity_user_management.*
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ShippingActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var session:LoginPref
    val urlShipping:String = "http://192.168.1.5/android/add_shipping.php"
    val urlOrder:String = "http://192.168.1.5/android/add_order.php"
//    val totalSum = intent.getStringExtra("totalPrice")


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)
        session = LoginPref(this)
        val totalSum = intent.getStringExtra("totalPrice")
        btnShipping.setOnClickListener {
            shippingInfo()
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addOrder(totalSum: String?, id_shipping: Int) {
        val orderQueue = Volley.newRequestQueue(this)
        var order_status:Int = 0
        var order_total:Double = totalSum!!.toDouble()
        var user : HashMap<String,String> = session.getUserDetail()
        var email = user.get(LoginPref.USER_EMAIL)
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val order_date = currentDateTime.format(formatter)
        val id_shipping = id_shipping
        val id_coupon:Int = 0

        val jsonObject = JSONObject()
        jsonObject.put("order_status",order_status)
        jsonObject.put("email",email)
        jsonObject.put("order_date",order_date)
        jsonObject.put("order_total",order_total)
        jsonObject.put("id_shipping",id_shipping)
        jsonObject.put("id_coupon",id_coupon)


        val stringRequest = object : StringRequest(
            Method.POST, urlOrder,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")
                    val message = jsonObject.getString("message")
                    if (status == "success") {
                    } else {
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    println(e)
                }
            },
            { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return jsonObject.toString().toByteArray(Charsets.UTF_8)
            }
        }

        orderQueue.add(stringRequest)
    }





    private fun shippingInfo() {
        val shipping_name = etShippingName.text.toString()
        val shipping_address = etShippingAddress.text.toString()
        val shipping_phone = etShippingPhone.text.toString()
        val shipping_note = etShippingNote.text.toString()
        val queue = Volley.newRequestQueue(this)

        val jsonObject = JSONObject()
        jsonObject.put("shipping_name", shipping_name)
        jsonObject.put("shipping_address", shipping_address)
        jsonObject.put("shipping_phone", shipping_phone)
        jsonObject.put("shipping_note", shipping_note)

        val stringRequest = @RequiresApi(Build.VERSION_CODES.O)
        object : StringRequest(
            Method.POST, urlShipping,
            { response ->
                // Handle the response from the server
                try {
                    val jsonObject = JSONObject(response)
                    val id_shipping = jsonObject.getInt("id_shipping")
                    val totalSum = intent.getStringExtra("totalPrice")
                    addOrder(totalSum,id_shipping.toInt())

                    val status = jsonObject.getString("status")
                    val message = jsonObject.getString("message")
                    if (status == "success") {

                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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