package com.example.skincareshopapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.skincareshopapp.R
import com.example.skincareshopapp.model.Product
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_product_info.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_user_management.*
import kotlinx.android.synthetic.main.list_product.nameProduct
import kotlinx.android.synthetic.main.list_product.priceProduct
import org.json.JSONArray
import org.json.JSONObject

class ProductInfoActivity : AppCompatActivity() {
    private lateinit var product: Product
    private lateinit var queue: RequestQueue
    private lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)

        // session of login
        session = LoginPref(this)



        // display information of product
        val productId = intent.getStringExtra("id_product")
        val urlString: String =
            "${Constants.url}get_product_info.php?id_product="+productId
        val imagePath = findViewById<ImageView>(R.id.imageProduct)
        queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlString, { response ->
                var jsonArray: JSONArray = JSONArray(response)
                var name_product: String = ""
                var id_product: String
                var number: String
                var price: String
                var describe_product: String
                var show_product: String
                var id_category_product: String
                var product_image: String
                for (product in 0..jsonArray.length() - 1) {
                    var objectProduct: JSONObject = jsonArray.getJSONObject(product)
                    id_product = objectProduct.getString("id_product")
                    name_product = objectProduct.getString("name_product")
                    show_product = objectProduct.getString("show_product")
                    number = objectProduct.getString("number")
                    price = objectProduct.getString("price")
                    describe_product = objectProduct.getString("describe_product")
                    product_image = objectProduct.getString("product_image")
                    id_category_product = objectProduct.getString("id_category_product")
                    nameProduct.text = name_product.toString()
                    priceProduct.text = price.toString()
                    describeProduct.text = describe_product.toString()
                    Glide.with(this)
                        .load("http://192.168.1.6/doancs2/public/public/admin/image/product/" + product_image)
                        .into(imagePath)
                    println(product)
                }


            }, { error ->
                println(error.message)
            })
        queue.add(request)



        // checked login before add to cart
        btnCart.setOnClickListener {
            val isLogged = session.isLogged()
            var user : HashMap<String,String> = session.getUserDetail()
            var email = user.get(LoginPref.USER_EMAIL)

            if(isLogged==false){
                val intent = Intent(this,LoginActivity::class.java)
                intent.putExtra("id_product",productId.toString())
                startActivity(intent)
            }
            else if (isLogged==true){
                val intent = Intent(this,CartActivity::class.java)
                intent.putExtra("id_product",productId.toString())
                startActivity(intent)
                val Cartqueue:RequestQueue
                addToCart()

            }

        }
    }


    private fun addToCart() {
        val id_product = intent.getStringExtra("id_product")
        val quantity:Int = 1
        val user : HashMap<String,String> = session.getUserDetail()
        var email = user.get(LoginPref.USER_EMAIL).toString()

        val Cartqueue = Volley.newRequestQueue(this)

        val urlCartAdd: String = "${Constants.url}add_to_cart.php"

        val stringRequest = object : StringRequest(Request.Method.POST, urlCartAdd,
            Response.Listener<String> { response ->
                val intent = Intent(this,CartActivity::class.java)
                startActivity(intent)
                Log.d("This", "Response: $response")
            },
            Response.ErrorListener { error ->
                Log.e("This", "Error: ${error.message}")
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id_product"] = id_product.toString()
                params["quantity"] = quantity.toString()
                params["email"] = email
                return params
            }
        }

        Cartqueue.add(stringRequest)
    }



    }

