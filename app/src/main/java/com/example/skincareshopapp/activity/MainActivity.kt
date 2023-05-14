package com.example.skincareshopapp.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.activity.userManagement.UserManagementActivity
import com.example.skincareshopapp.adapter.CategoryProductAdapter
import com.example.skincareshopapp.adapter.ProductAdapter
import com.example.skincareshopapp.adapter.RecommendProductAdapter
import com.example.skincareshopapp.adapter.ViewPagerAdapter
import com.example.skincareshopapp.model.CategoryProductModel
import com.example.skincareshopapp.model.Product
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_drawer.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CategoryProductAdapter
    private lateinit var recommendAdapter: RecommendProductAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var categoryList: MutableList<CategoryProductModel>
    val urlGetData: String = "${Constants.url}get_categoty_product.php"
    private lateinit var queue: RequestQueue
    private lateinit var loginSession: LoginPref
    private lateinit var productRecommend:MutableList<Product>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.skincareshopapp.R.layout.activity_main)
        loginSession = LoginPref(this)
        recommendProduct()
        //  navigation view
        setSupportActionBar(toolbarHome)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbarHome.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        toolbarHome.setNavigationOnClickListener(View.OnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        })




        // navigation bottom
        val navigationBottom : BottomNavigationView = findViewById(com.example.skincareshopapp.R.id.bottom_navigation)
        navigationBottom.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                com.example.skincareshopapp.R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                com.example.skincareshopapp.R.id.nav_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    true
                }
                com.example.skincareshopapp.R.id.nav_shop -> {
                    val intent = Intent(this,ShopActivity::class.java)
                    startActivity(intent)
                    true
                }
                com.example.skincareshopapp.R.id.nav_profile->{
                    val intent = Intent(this,UserManagementActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // slider
        val viewPager: ViewPager2 = findViewById(com.example.skincareshopapp.R.id.imageRecycler)
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

    private fun recommendProduct() {
        listRecommend.layoutManager = GridLayoutManager(this,2)
       productRecommend = mutableListOf()
        val urlRecommend:String="${Constants.url}recommend.php"
        val queueRecommend:RequestQueue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlRecommend, { response ->
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
                    productRecommend.add(Product(id_product.toInt(), name_product, number.toInt(),show_product.toBoolean(),price.toDouble(),describe_product,product_image,id_category_product.toInt()))

                }

                // fetch API xog add vào products r mới gán cho adapter
                recommendAdapter= RecommendProductAdapter(this,productRecommend)
                listRecommend.adapter = recommendAdapter


            }, { error ->
                println(error.message)
            })
        queueRecommend.add(request)
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