package com.example.snack_rachel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class VerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        // Ambil data yang dikirim dari RegisterActivity
        val phoneRegistrasi = intent.getStringExtra("EXTRA_PHONE") ?: ""
        val usernameRegistrasi = intent.getStringExtra("EXTRA_USERNAME") ?: ""
        val passwordRegistrasi = intent.getStringExtra("EXTRA_PASSWORD") ?: ""

        val etOtp = findViewById<EditText>(R.id.etOtp)
        val btnVerify = findViewById<Button>(R.id.btnVerify)

        btnVerify.setOnClickListener {
            val inputOtp = etOtp.text.toString().trim()

            // SOAL 2: Jika OTP sama dengan Nomor Handphone saat registrasi
            if (inputOtp.isNotEmpty() && inputOtp == phoneRegistrasi) {

                // SIMPAN DATA KE SHAREDPREFERENCES (Soal 2)
                val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("KEY_USERNAME", usernameRegistrasi)
                    putString("KEY_PASSWORD", passwordRegistrasi)
                    apply()
                }

                // Arahkan ke LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()

            } else {
                // SOAL 2: Tampilkan MaterialAlertDialog jika beda atau kosong
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP tidak sesuai dengan nomor handphone!")
                    .setPositiveButton("Coba Lagi") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}