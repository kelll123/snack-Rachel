package com.example.snack_rachel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.snack_rachel.onboarding.OnboardAdapter

class OnboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerOnboard)
        val adapter = OnboardAdapter(this)
        viewPager.adapter = adapter
    }
}