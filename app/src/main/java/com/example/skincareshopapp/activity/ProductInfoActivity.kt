package com.example.skincareshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skincareshopapp.R
import com.example.skincareshopapp.model.Product

class ProductInfoActivity : AppCompatActivity() {
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
        val productId = intent.getStringExtra("id_product")
        val urlString:String = "http://192.168.43.10/android/get_product_info.php?id_product="+productId
    }
}