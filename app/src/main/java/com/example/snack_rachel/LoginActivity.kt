package com.example.snack_rachel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnToRegister = findViewById<Button>(R.id.btnToRegister)

        // Navigasi ke Halaman Registrasi
        btnToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val inputUsername = etUsername.text.toString().trim()
            val inputPassword = etPassword.text.toString().trim()

            // 1
            val sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val savedUsername = sharedPreferences.getString("KEY_USERNAME", "")
            val savedPassword = sharedPreferences.getString("KEY_PASSWORD", "")

            // 2
            val isRule1Valid = inputUsername.isNotEmpty() && inputUsername == inputPassword

            // 3
            val isRule2Valid = inputUsername == savedUsername && inputPassword == savedPassword

            if (isRule1Valid || isRule2Valid) {
                // Simpan status session login
                val sessionPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                val editor = sessionPref.edit()
                editor.putBoolean("isLogin", true)
                editor.apply()

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                // Diarahkan ke BaseActivity
                val intent = Intent(this, BaseActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}