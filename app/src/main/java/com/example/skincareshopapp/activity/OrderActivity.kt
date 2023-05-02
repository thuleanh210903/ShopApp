package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skincareshopapp.R
import com.example.skincareshopapp.utilities.Constants

class OrderActivity : AppCompatActivity() {
    val urlOrder:String = "${Constants.url}add_order.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        println(intent.getStringExtra("total"))


    }
}