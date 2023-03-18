package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.CategoryProductAdapter
import com.example.skincareshopapp.model.CategoryProductModel
import com.example.skincareshopapp.model.Product
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CategoryProductAdapter
    private lateinit var categoryList: ArrayList<CategoryProductModel>
    val urlGetData: String="http://192.168.1.11/android/get_categoty_product.php"
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryList = ArrayList()
        queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlGetData, { response ->
                var jsonArray: JSONArray = JSONArray(response)
                var name_category_product:String = ""
                var id:String
                var show: String
                for(category in 0..jsonArray.length()-1){
                    var objectCategory:JSONObject = jsonArray.getJSONObject(category)
                    id = objectCategory.getString("id_category_product")
                    name_category_product = objectCategory.getString("name_category_product")
                    show = objectCategory.getString("show")
                    categoryList.add(CategoryProductModel(id.toInt(), name_category_product, show.toBoolean()))
                }
            }, { error ->
                println(error.message)
            })
        queue.add(request)
        listCategoryRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapter = CategoryProductAdapter(this@MainActivity, categoryList)
        listCategoryRecyclerView.adapter = adapter
    }
}