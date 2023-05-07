package com.example.skincareshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.activity.userManagement.UserManagementActivity
import com.example.skincareshopapp.adapter.CategoryProductAdapter
import com.example.skincareshopapp.adapter.ViewPagerAdapter
import com.example.skincareshopapp.model.CategoryProductModel
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Time
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CategoryProductAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var categoryList: MutableList<CategoryProductModel>
    val urlGetData: String = "${Constants.url}get_categoty_product.php"
    private lateinit var queue: RequestQueue
    private lateinit var loginSession: LoginPref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationBottom : BottomNavigationView = findViewById(R.id.bottom_navigation)
        navigationBottom.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        val viewPager: ViewPager2 = findViewById(R.id.imageRecycler)

        val images = listOf(
            "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2022/03/tieu-su-tempest-700x420.jpg?fit=700%2C20000&quality=95&ssl=1",
            "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2022/03/tempest-hanbin-profile-696x975.jpeg?fit=700%2C20000&quality=95&ssl=1",
        )

        val imageAdapter = ViewPagerAdapter(images)
        viewPager.adapter = imageAdapter

// Optional: add auto-scrolling to the ViewPager2
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            if (viewPager.currentItem < imageAdapter.itemCount - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                viewPager.currentItem = 0
            }
        }
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, 3000, 3000)

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