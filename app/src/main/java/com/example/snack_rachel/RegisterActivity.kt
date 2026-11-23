package com.example.snack_rachel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.snack_rachel.data.api.SupabaseClient
import com.example.snack_rachel.data.model.UserRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Sesuaikan ID ini dengan ID yang ada di activity_register.xml kamu
        val etNama = findViewById<EditText>(R.id.etNamaLengkap)
        val etUsername = findViewById<EditText>(R.id.etRegUsername)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val btnDaftar = findViewById<Button>(R.id.btnDaftar)

        btnDaftar?.setOnClickListener {
            val nama = etNama?.text.toString().trim()
            val username = etUsername?.text.toString().trim()
            val password = etPassword?.text.toString().trim()

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnDaftar.isEnabled = false
            btnDaftar.text = "Mendaftarkan..."

            lifecycleScope.launch {
                try {
                    val userBaru = UserRequest(
                        username = username,
                        password = password,
                        nama_lengkap = nama
                    )
                    val response = SupabaseClient.apiService.registerUser(userBaru)

                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Registrasi ke Supabase Berhasil!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Gagal daftar (${response.code()})", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, "Error jaringan: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    btnDaftar.isEnabled = true
                    btnDaftar.text = "DAFTAR"
                }
            }
        }
    }
}