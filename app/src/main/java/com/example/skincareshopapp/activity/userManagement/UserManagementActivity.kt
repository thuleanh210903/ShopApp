package com.example.skincareshopapp.activity.userManagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.activity.LoginActivity
import com.example.skincareshopapp.activity.MainActivity
import com.example.skincareshopapp.activity.ProductInfoActivity
import com.example.skincareshopapp.model.Product
import com.example.skincareshopapp.model.User
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_user_management.*
import org.json.JSONArray
import org.json.JSONObject

class UserManagementActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        session = LoginPref(this)
        //get email user
        var emailSession = intent.getStringExtra("email")
        logOut.setOnClickListener {
            session.logOut()
            val intent = Intent (this,MainActivity::class.java)
            startActivity(intent)

        }
        orders.setOnClickListener {
            val intent = Intent(this,OrderUserActivity::class.java)
            startActivity(intent)

        }
        profile.setOnClickListener {
            val intent = Intent(this,UserProfileActivity::class.java)
            startActivity(intent)

        }

        //api get information by email
//        val urlUserId:String = "${Constants.url}get_User.php?email=" + emailSession
//
//        queue = Volley.newRequestQueue(this)
//        val request = StringRequest(
//            Request.Method.GET, urlUserId, { response ->
//                var jsonArray: JSONArray = JSONArray(response)
//                var email: String = ""
//                var userId: String
//                var username: String
//                var password: String
//                var created_at:String
//                var updated_at:String
//                var address: String
//                var numberphone: String
//                var otp: String
//                var status: String
//                for (user in 0..jsonArray.length() - 1) {
//                    var objectUser: JSONObject = jsonArray.getJSONObject(user)
//                    userId = objectUser.getString("user_id")
//                    Log.d("userId: ", userId)
//                    email = objectUser.getString("email")
//                    username = objectUser.getString("username")
//                    password = objectUser.getString("password")
//                    created_at = objectUser.getString("created_at")
//                    updated_at = objectUser.getString("updated_at")
//                    address = objectUser.getString("address")
//                    numberphone = objectUser.getString("numberphone")
//                    otp = objectUser.getString("otp")
//                    status = objectUser.getString("status")
//
//                    tvUserName.text = username.toString()
//                    intent.putExtra("user_id",userId)
//                }
//            }, { error ->
//                println(error.message)
//            })
//
//        queue.add(request)



    }
}