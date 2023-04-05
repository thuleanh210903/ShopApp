package com.example.skincareshopapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.session.LoginPref
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Request


class LoginActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private var urlLogin:String= "http://192.168.1.7/android/login.php"
//    private lateinit var sharedPreferences: SharedPreferences
//    lateinit var editor:SharedPreferences.Editor
    private lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        session = LoginPref(this)

        tvSignUp.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            logIn()
        }


    }


    private fun logIn() {

        val email = editEmailUser.text.toString()
        val password = editUserPassword.text.toString()
        val queue = Volley.newRequestQueue(this)

// Request a string response from the provided URL.
        val stringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, urlLogin,
            Response.Listener<String> { response ->
                session.createLoginSession(email,password)
                val intent = Intent(applicationContext,CartActivity::class.java)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Login Faile",Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["password"] = password
                return params
            }
        }

        queue.add(stringRequest)
    }
}

