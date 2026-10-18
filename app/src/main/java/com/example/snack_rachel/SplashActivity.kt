package com.example.snack_rachel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val isLogin = sharedPreferences.getBoolean("isLogin", false)

            // Jika sudah login, arahkan langsung ke BaseActivity (Bottom Navigation)
            val intent = if (isLogin) {
                Intent(this, BaseActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }

            startActivity(intent)
            finish()
        }, 2000)
    }
}