package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.CategoryProductAdapter
import com.example.skincareshopapp.model.CategoryProductModel
import com.example.skincareshopapp.session.LoginPref
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_category_product.*
import kotlinx.android.synthetic.main.list_product.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CategoryProductAdapter
    private lateinit var categoryList: MutableList<CategoryProductModel>
    val urlGetData: String = "http://192.168.1.7/android/get_categoty_product.php"
    private lateinit var queue: RequestQueue
    private lateinit var itemList: ArrayList<Int>
    private lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        session = LoginPref(this)
        var user: HashMap<String, String> = session.getUserDetail()
        val email = user.get(LoginPref.USER_EMAIL)
        tvUserName.setText(email)




        categoryList = mutableListOf()
        queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlGetData, { response ->
                var jsonArray: JSONArray = JSONArray(response)
                var name_category_product: String = ""
                var id: String? = null
                var show: String? = null
                var image: String? = null
                for (category in 0..jsonArray.length() - 1) {
                    var objectCategory: JSONObject = jsonArray.getJSONObject(category)
                    id = objectCategory.getString("id_category_product")
                    name_category_product = objectCategory.getString("name_category_product")
                    show = objectCategory.getString("show")
                    image = objectCategory.getString("image")
                    categoryList.add(
                        CategoryProductModel(
                            id.toInt(),
                            name_category_product,
                            show.toBoolean(),
                            image
                        )
                    )

                }
            }, { error ->
                println(error.message)
            })
        queue.add(request)

        listCategoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = CategoryProductAdapter(this, categoryList)
        listCategoryRecyclerView.setHasFixedSize(true)
        listCategoryRecyclerView.adapter = adapter


        // view image banner


        fun addToList() {
            itemList.add(R.drawable.pic5)


        }
    }
}