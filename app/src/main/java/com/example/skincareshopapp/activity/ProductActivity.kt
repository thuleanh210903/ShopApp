package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.CategoryProductAdapter
import com.example.skincareshopapp.adapter.ProductAdapter
import com.example.skincareshopapp.model.CategoryProductModel
import com.example.skincareshopapp.model.Product
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_product.*
import org.json.JSONArray
import org.json.JSONObject

class ProductActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter
    private lateinit var products:ArrayList<Product>
    val urlData:String="http://192.168.1.11/android/get_product_by_cate.php"
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        //get category id
        val categoryId = intent.getStringExtra("id_category_product")
        products = ArrayList()
        queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlData, { response ->
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
                    var objectProduct:JSONObject = jsonArray.getJSONObject(product)
                    id_product = objectProduct.getString("id_product")
                    name_product = objectProduct.getString("name_product")
                    show_product = objectProduct.getString("show_product")
                    number = objectProduct.getString("number")
                    price = objectProduct.getString("price")
                    describe_product = objectProduct.getString("describe_product")
                    product_image = objectProduct.getString("product_image")
                    id_category_product= objectProduct.getString("id_category_product")

                    if(id_category_product.toInt()==categoryId?.toInt()){
                        products.add(Product(id_product.toInt(), name_product, number.toInt(),show_product.toBoolean(),price.toDouble(),describe_product,product_image,id_category_product.toInt()))
                    }
                }
            }, { error ->
                println(error.message)
            })
        queue.add(request)
        listProduct.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        adapter = ProductAdapter(this@ProductActivity,products)
        listProduct.adapter = adapter
        adapter.notifyDataSetChanged()



    }
}