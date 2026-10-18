package com.example.snack_rachel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGoToMenu = view.findViewById<Button>(R.id.btnGoToMenu)
        val btnGoToWeb = view.findViewById<Button>(R.id.btnGoToWeb)
        val btnGoToProfile = view.findViewById<Button>(R.id.btnGoToProfile)
        val btnLogoutUser = view.findViewById<Button>(R.id.btnLogoutUser)

        btnGoToMenu?.setOnClickListener {
            startActivity(Intent(requireContext(), MenuActivity::class.java))
        }

        btnGoToWeb?.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }

        btnGoToProfile?.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        btnLogoutUser?.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin keluar dari akun?")
        builder.setCancelable(false)

        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()

            val sharedPreferences = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLogin", false)
            editor.apply()

            Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}