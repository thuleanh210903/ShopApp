package com.example.skincareshopapp.activity

import android.content.ContextParams
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.ProductAdapter
import com.example.skincareshopapp.model.Product
import com.example.skincareshopapp.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var queue: RequestQueue
    val urlRegisterString: String = "${Constants.url}register.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnRegister.setOnClickListener {
            register()
        }

    }

    private fun register() {

        val username = userName.text.toString()
        val email = emailUser.text.toString()
        val password = userPassword.text.toString()
        val phone = userPhone.text.toString()
        if (username.isEmpty()) {
            userName.error = "Username Required"
        } else if (email.isEmpty()) {
            emailUser.error = "Email Required"
        } else if (password.isEmpty()) {
            userPassword.error = "Password required"
        }
        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(this) { task ->
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { verificationTask ->
                            if (verificationTask.isSuccessful) {
                                Toast.makeText(this,"User registered successfully",Toast.LENGTH_SHORT).show()
                            } else {
                                // Verification email sending failed
                            }
                        }

                        sendUserToServer(username, email, password, phone)



                }
                .addOnFailureListener(this) { exception ->
                    Toast.makeText(this,"Registration failed: ${exception.message}",Toast.LENGTH_SHORT).show()
                }
        }
    }
    private fun sendUserToServer(username: String, email: String, password: String, phone: String) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(Request.Method.POST, urlRegisterString,
            Response.Listener<String> { response ->
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Log.d("This", "Response: $response")
            },
            Response.ErrorListener { error ->
                Log.e("This", "Error: ${error.message}")
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["email"] = email
                params["password"] = password
                params["phone"] = phone
                return params
            }
        }

        queue.add(stringRequest)
    }
}

