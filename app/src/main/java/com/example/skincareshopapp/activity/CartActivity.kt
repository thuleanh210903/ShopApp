package com.example.skincareshopapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.CartAdapter
import com.example.skincareshopapp.model.Coupon
import com.example.skincareshopapp.model.ProductCartInfo
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_cart.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt

class CartActivity : AppCompatActivity() {
    private lateinit var cartList:ArrayList<ProductCartInfo>
    private lateinit var adapter:CartAdapter
    private lateinit var coupon: Coupon
    private lateinit var loginSession: LoginPref
    private lateinit var queue: RequestQueue
    private var totalSum : Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // get email user
        loginSession = LoginPref(this)

        var user : HashMap<String,String> = loginSession.getUserDetail()
        var email = user.get(LoginPref.USER_EMAIL)
        var password = user.get(LoginPref.USER_PASSWORD)

        // cart display
        cartList = ArrayList()
        val urlCart: String = "${Constants.url}display_cart.php?email="+email
        queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlCart, { response ->
                var jsonArray: JSONArray = JSONArray(response)
                var email: String = ""
                var id_cart: String? = ""
                var quantity: String ?= null
                var id_product: String? = null
                for (cart in 0..jsonArray.length() - 1) {
                    var objectCategory: JSONObject = jsonArray.getJSONObject(cart)
                    id_cart = objectCategory.getString("id_cart")
                    email = objectCategory.getString("email")
                    quantity = objectCategory.getString("quantity")
                    id_product = objectCategory.getString("id_product")
                    if(id_cart==null){
                        Toast.makeText(this,"Please choose product which you want to buy",Toast.LENGTH_SHORT).show()
                    }
                    getInfoProduct(id_cart.toString(),id_product.toString(), quantity.toInt())
                    totalPrice(id_product.toString(),quantity.toInt())


                }

            }, { error ->
                println(error.message)
            })
        queue.add(request)



    }

    private fun totalPrice(id_product: String, quantity: Int) {

        var priceByQuantity:Double = 0.0
        val priceQueue:RequestQueue
        val urlPrice: String = "${Constants.url}get_price_product.php?id_product="+ id_product
        priceQueue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlPrice, { response ->
                val jsonArray: JSONArray = JSONArray(response)
                var id_product: String
                var price: String
                for (priceCart in 0..jsonArray.length()-1) {
                    var objectProduct: JSONObject = jsonArray.getJSONObject(priceCart)
                    price = objectProduct.getString("price")
                    priceByQuantity = price.toDouble()*quantity


                }
                totalSum += priceByQuantity
                subTotal.text = totalSum.toString()
                btnOrder.setOnClickListener {
                    val intent = Intent(this,ShippingActivity::class.java)
                    intent.putExtra("totalPrice",totalSum.toString())
                    startActivity(intent)
                }


            }


            , { error ->
                println(error.message)
            })

        priceQueue.add(request)
    }





    private fun getInfoProduct(idCart:String,idProduct: String, quantity: Int) {

        val cartQueue:RequestQueue
        val urlProduct: String = "${Constants.url}get_product_info.php?id_product="+ idProduct
        val imagePath = findViewById<ImageView>(R.id.imageProductCart)
        cartQueue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlProduct, { response ->
                val jsonArray: JSONArray = JSONArray(response)
                var name_product: String = ""
                var id_product: String
                var number: String
                var price: String
                var describe_product: String
                var show_product: String
                var id_category_product: String
                var product_image: String

                for (productCart in 0..jsonArray.length()-1) {
                    var objectProduct: JSONObject = jsonArray.getJSONObject(productCart)
                    id_product = objectProduct.getString("id_product")
                    name_product = objectProduct.getString("name_product")
                    show_product = objectProduct.getString("show_product")
                    number = objectProduct.getString("number")
                    price = objectProduct.getString("price")
                    describe_product = objectProduct.getString("describe_product")
                    product_image = objectProduct.getString("product_image")
                    id_category_product = objectProduct.getString("id_category_product")
                    cartList.add(ProductCartInfo(idCart.toInt(),idProduct.toInt(),name_product,price.toDouble(),product_image,quantity))
//                    totalPrice(price.toDouble())
                }
                listCartItemRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                adapter = CartAdapter(this,cartList,this)
                listCartItemRecyclerView.adapter = adapter
                }
            , { error ->
                println(error.message)
            })
        cartQueue.add(request)
    }

//

}