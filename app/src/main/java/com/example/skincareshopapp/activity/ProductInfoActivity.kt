package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.ProductAdapter
import com.example.skincareshopapp.model.Product
import kotlinx.android.synthetic.main.activity_product_info.*
import kotlinx.android.synthetic.main.list_product.*
import kotlinx.android.synthetic.main.list_product.nameProduct
import kotlinx.android.synthetic.main.list_product.priceProduct
import org.json.JSONArray
import org.json.JSONObject

class ProductInfoActivity : AppCompatActivity() {
    private lateinit var product: Product
    private lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
        val productId = intent.getStringExtra("id_product")
        val urlString:String = "http://192.168.1.11/android/get_product_info.php?id_product="+productId
        val imagePath = findViewById<ImageView>(R.id.imageProduct)
        queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlString, { response ->
                var jsonArray: JSONArray = JSONArray(response)
                var name_product:String = ""
                var id_product:String
                var number:String
                var price:String
                var describe_product:String
                var show_product:String
                var id_category_product:String
                var product_image:String
                for(product in 0..jsonArray.length()-1){
                    var objectProduct: JSONObject = jsonArray.getJSONObject(product)
                    id_product = objectProduct.getString("id_product")
                    name_product = objectProduct.getString("name_product")
                    show_product = objectProduct.getString("show_product")
                    number = objectProduct.getString("number")
                    price = objectProduct.getString("price")
                    describe_product = objectProduct.getString("describe_product")
                    product_image = objectProduct.getString("product_image")
                    id_category_product= objectProduct.getString("id_category_product")
                    nameProduct.text = name_product.toString()
                    priceProduct.text = price.toString()
                    describeProduct.text = describe_product.toString()
                    Glide.with(this).load("http://192.168.1.11/doancs2/public/public/admin/image/product/"+product_image).into(imagePath)
                }


            }, { error ->
                println(error.message)
            })
        queue.add(request)

    }
}