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
            // Membaca SharedPreferences sesuai Modul Pertemuan 6
            val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val isLogin = sharedPreferences.getBoolean("isLogin", false)

            if (isLogin) {
                // Jika sudah login -> Langsung ke MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Jika belum login -> Ke LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }
}