package com.example.skincareshopapp.activity

import android.content.ContextParams
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.ProductAdapter
import com.example.skincareshopapp.model.Product
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    val urlRegisterString: String = "http://192.168.1.7/android/register.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnRegister.setOnClickListener {
            register()
        }

    }

    private fun register() {

        val userName = userName.text.toString()
        val email = emailUser.text.toString()
        val password = userPassword.text.toString()
        val phone = userPhone.text.toString()

        val queue = Volley.newRequestQueue(this)

// Request a string response from the provided URL.
        val stringRequest = object : StringRequest(Request.Method.POST, urlRegisterString,
            Response.Listener<String> { response ->
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                Log.d("This", "Response: $response")
            },
            Response.ErrorListener { error ->
                Log.e("This", "Error: ${error.message}")
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = userName
                params["email"] = email
                params["password"] = password
                params["phone"] = phone
                return params
            }
        }

        queue.add(stringRequest)
        }
    }
