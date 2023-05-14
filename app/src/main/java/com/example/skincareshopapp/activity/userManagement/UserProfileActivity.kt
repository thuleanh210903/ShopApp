package com.example.skincareshopapp.activity.userManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.session.LoginPref
import com.example.skincareshopapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_user_management.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.tvPhone
import org.json.JSONArray
import org.json.JSONObject

class UserProfileActivity : AppCompatActivity() {
    private lateinit var loginSession: LoginPref
    private lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        loginSession = LoginPref(this)
        var user : HashMap<String,String> = loginSession.getUserDetail()
        var email = user.get(LoginPref.USER_EMAIL)


        //api get information by email
        val urlUserId:String = "${Constants.url}get_User.php?email=" + email

        queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlUserId, { response ->
                var jsonArray: JSONArray = JSONArray(response)
                var email: String = ""
                var userId: String
                var username: String
                var password: String
                var created_at:String
                var updated_at:String
                var address: String
                var numberphone: String
                var otp: String
                var status: String
                for (user in 0..jsonArray.length() - 1) {
                    var objectUser: JSONObject = jsonArray.getJSONObject(user)
                    userId = objectUser.getString("user_id")
                    Log.d("userId: ", userId)
                    email = objectUser.getString("email")
                    username = objectUser.getString("username")
                    password = objectUser.getString("password")
                    created_at = objectUser.getString("created_at")
                    updated_at = objectUser.getString("updated_at")
                    address = objectUser.getString("address")
                    numberphone = objectUser.getString("numberphone")
                    otp = objectUser.getString("otp")
                    status = objectUser.getString("status")

                    tvPhone.text = numberphone
                    tvPassword.text = password
                    tvMail.text = email

                }
            }, { error ->
                println(error.message)
            })

        queue.add(request)

    }
}