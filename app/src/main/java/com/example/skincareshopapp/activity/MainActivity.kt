package com.example.skincareshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.activity.userManagement.UserManagementActivity
import com.example.skincareshopapp.adapter.CategoryProductAdapter
import com.example.skincareshopapp.model.CategoryProductModel
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Time
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CategoryProductAdapter
    private lateinit var categoryList: MutableList<CategoryProductModel>
    val urlGetData: String = "${Constants.url}get_categoty_product.php"
    private lateinit var queue: RequestQueue
    private lateinit var loginSession: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        loginSession = LoginPref(this)
//        val user: HashMap<String, String> = loginSession.getUserDetail()
//        val email = user.get(LoginPref.USER_EMAIL)
//        btnAccount.setOnClickListener {
//            val intent = Intent(this, UserManagementActivity::class.java)
//            intent.putExtra("email", email)
//            startActivity(intent)
//            finish()
//        }




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
                listCategoryRecyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                adapter = CategoryProductAdapter(this, categoryList)
                listCategoryRecyclerView.setHasFixedSize(true)
                listCategoryRecyclerView.adapter = adapter
            }, { error ->
                println(error.message)
            })
        queue.add(request)

    }



    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask
    private var currentIndex = 0

    private fun startAutoScroll(){
        timer = Timer()
        timerTask = object  : TimerTask(){
            override fun run(){
                runOnUiThread {
                    if(currentIndex < categoryList.size -1){
                        currentIndex ++
                    }else{
                        currentIndex = 0
                    }
                    listCategoryRecyclerView.smoothScrollToPosition(currentIndex)
                }
            }
        }
        timer.schedule(timerTask,3000, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        timerTask.cancel()
    }

}