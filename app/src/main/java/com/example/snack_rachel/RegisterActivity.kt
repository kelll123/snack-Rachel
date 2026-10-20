package com.example.snack_rachel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNama = findViewById<EditText>(R.id.etNama)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegisterSubmit = findViewById<Button>(R.id.btnRegisterSubmit)

        btnRegisterSubmit.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (nama.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua inputan wajib diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, VerificationActivity::class.java).apply {
                    putExtra("EXTRA_NAMA", nama)
                    putExtra("EXTRA_PHONE", phone)
                    putExtra("EXTRA_USERNAME", username)
                    putExtra("EXTRA_PASSWORD", password)
                }
                startActivity(intent)
            }
        }
    }
}