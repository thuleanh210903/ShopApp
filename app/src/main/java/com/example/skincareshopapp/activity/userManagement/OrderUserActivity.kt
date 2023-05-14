package com.example.skincareshopapp.activity.userManagement

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.OrderAdapter
import com.example.skincareshopapp.model.Orders
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_order_user.*
import kotlinx.android.synthetic.main.order_item.*
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class OrderUserActivity : AppCompatActivity() {
    private lateinit var loginSession:LoginPref
    private lateinit var adapter: OrderAdapter
    private lateinit var orders:ArrayList<Orders>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_user)
        loginSession = LoginPref(this)
        var user : HashMap<String,String> = loginSession.getUserDetail()
        var email = user.get(LoginPref.USER_EMAIL)


        orders = ArrayList()
        val urlDisplayOrder:String="${Constants.url}display_order_email.php?email="+email
        val queueOrder: RequestQueue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlDisplayOrder, { response ->
                var jsonArray: JSONArray = JSONArray(response)
                var id_order:String=""
                var order_total:String=""
                var order_status:String=""
                var order_date:String=""
                var id_shipping:String=""
                var id_coupon:String=""
                for(order in 0..jsonArray.length()-1){
                    var objectOrder: JSONObject = jsonArray.getJSONObject(order)
                    id_order = objectOrder.getString("id_order")
                    order_total= objectOrder.getString("order_total")
                    order_status = objectOrder.getString("order_status")
                    order_date = objectOrder.getString("order_date")
                    id_shipping = objectOrder.getString("id_shipping")
                    id_coupon = objectOrder.getString("id_coupon")
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val date = LocalDateTime.parse(order_date, formatter)
                    orders.add(Orders(id_order.toInt(),email,order_status.toInt(), order_total.toDouble(),date, id_shipping.toInt(),id_coupon.toInt()))



                }
                listOrderByUser.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                adapter = OrderAdapter(this,orders)
                listOrderByUser.adapter = adapter
            },{
                    error ->
                println(error.message)
            }
        )
        queueOrder.add(request)
    }
}