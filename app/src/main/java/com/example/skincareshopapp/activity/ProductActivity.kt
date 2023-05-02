package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
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
import com.example.skincareshopapp.utilities.Constants
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_product.*
import org.json.JSONArray
import org.json.JSONObject

class ProductActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter
    private lateinit var products:ArrayList<Product>
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        //get category id
        val listRecyclerViewProduct = findViewById<RecyclerView>(R.id.listProductRecyclerView)
        val categoryId = intent.getStringExtra("id_category_product")
        val urlData:String="${Constants.url}get_product_by_cate.php?id_category_product=" + categoryId
        products = ArrayList()

        // thiết lập layout trc khi render
//        listRecyclerViewProduct.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        listRecyclerViewProduct.layoutManager = GridLayoutManager(this,2)

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
                    products.add(Product(id_product.toInt(), name_product, number.toInt(),show_product.toBoolean(),price.toDouble(),describe_product,product_image,id_category_product.toInt()))

                }

                // fetch API xog add vào products r mới gán cho adapter
                adapter = ProductAdapter(this,products)
                listRecyclerViewProduct.adapter = adapter


            }, { error ->
                println(error.message)
            })
        queue.add(request)
    }
}