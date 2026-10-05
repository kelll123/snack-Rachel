package com.example.snack_rachel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Hubungkan Toolbar dari activity_menu.xml
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarMenu)
        setSupportActionBar(toolbar)

        // Aktifkan Tombol Back (Up Button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Aksi saat tombol back diklik
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}