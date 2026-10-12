package com.example.snack_rachel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGoToMenu = findViewById<Button>(R.id.btnGoToMenu)
        val btnGoToWeb = findViewById<Button>(R.id.btnGoToWeb)
        val btnGoToProfile = findViewById<Button>(R.id.btnGoToProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnGoToMenu.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        btnGoToWeb.setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
        }

        btnGoToProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin keluar dari akun?")
        builder.setCancelable(true)

        builder.setPositiveButton("Ya") { _, _ ->
            val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLogin", false)
            editor.apply()

            Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}