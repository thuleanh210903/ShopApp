package com.example.skincareshopapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skincareshopapp.R
import com.example.skincareshopapp.session.LoginPref
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private lateinit var session: LoginPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        session = LoginPref(this)
        var user : HashMap<String,String> = session.getUserDetail()
        var email = user.get(LoginPref.USER_EMAIL)
        var password = user.get(LoginPref.USER_PASSWORD)
        tvCart.setText(email)
        btnOrder.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}