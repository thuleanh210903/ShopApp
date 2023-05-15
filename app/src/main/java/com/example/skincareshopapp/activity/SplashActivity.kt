package com.example.skincareshopapp.activity

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.skincareshopapp.R
import com.example.skincareshopapp.adapter.SplashAdapter
import com.example.skincareshopapp.adapter.ViewPagerAdapter
import com.example.skincareshopapp.model.IntroSlide
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    private lateinit var introAdapter: SplashAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val viewPager: ViewPager2 = findViewById(com.example.skincareshopapp.R.id.slideViewPager)
        val introSlides = listOf(
            IntroSlide("Healthy skin, beautiful you", R.drawable.splash1),
            IntroSlide("Skincare for tomorrow, delivered today", R.drawable.splash2),
            IntroSlide("Your order, your way. Online every day", R.drawable.splash3)
        )

         introAdapter = SplashAdapter(introSlides)
        viewPager.adapter = introAdapter
        setUpIndicators()
        setCurrentIndicators(0)
        viewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicators(position)
            }
        })
        btnNext.setOnClickListener {
            if(viewPager.currentItem + 1 < introAdapter.itemCount){
                viewPager.currentItem +=1
            }else{
                Intent(applicationContext,MainActivity::class.java).also {
                    startActivity(it)
                }

            }
        }
        btnBack.setOnClickListener {
            if(viewPager.currentItem + 1 > introAdapter.itemCount){
                viewPager.currentItem -=1
            }
        }
        btnSkip.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }
    private fun setUpIndicators(){
        val indicators = arrayOfNulls<ImageView>(introAdapter.itemCount)
        val layoutParams:LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicator_layout.addView(indicators[i])
        }
    }
    private fun setCurrentIndicators(index:Int){
        val childCount = indicator_layout.childCount
        for(i in 0 until  childCount){
            val imageView = indicator_layout.get(i) as ImageView
            if(i == index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )

            }
        }

    }
}
