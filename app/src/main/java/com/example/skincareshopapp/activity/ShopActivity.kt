package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.ProductAdapter
import com.example.skincareshopapp.adapter.ShopAdapter
import com.example.skincareshopapp.model.Product
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_shop.*
import org.json.JSONArray
import org.json.JSONObject



class ShopActivity : AppCompatActivity() {
    private lateinit var adapter: ShopAdapter
    private lateinit var products:MutableList<Product>
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        //get category id
        val listShop = findViewById<RecyclerView>(R.id.listShop)
        val urlShop:String="${Constants.url}shop.php"
        products =  mutableListOf()

        // thiết lập layout trc khi render
//        listRecyclerViewProduct.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        listShop.layoutManager = GridLayoutManager(this,2)

        queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlShop, { response ->
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
                    products.add(Product(id_product.toInt(), name_product, number.toInt(),show_product.toBoolean(),price.toDouble(),describe_product,product_image,id_category_product.toInt()))

                }

                // fetch API xog add vào products r mới gán cho adapter
                adapter = ShopAdapter(this,products)
                listShop.adapter = adapter


            }, { error ->
                println(error.message)
            })
        queue.add(request)

        //search view
        searchProduct.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchProduct.clearFocus()
                if (products.any { it.name_product.equals(query, ignoreCase = true) }) {
                        adapter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return true
            }
        })
    }
}