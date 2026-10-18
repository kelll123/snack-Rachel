package com.example.snack_rachel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarProfile)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}