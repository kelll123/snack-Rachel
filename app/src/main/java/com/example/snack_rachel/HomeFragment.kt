package com.example.snack_rachel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGoToMenu = view.findViewById<View>(R.id.btnGoToMenu)
        val btnGoToWeb = view.findViewById<View>(R.id.btnGoToWeb)
        val btnGoToProfile = view.findViewById<View>(R.id.btnGoToProfile)
        val btnLogoutUser = view.findViewById<View>(R.id.btnLogoutUser)

        // Grid 1: Pindah ke Tab Menu di BottomNavigation
        btnGoToMenu?.setOnClickListener {
            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
            bottomNav?.selectedItemId = R.id.nav_menu
        }

        // Grid 2: Buka Halaman Web
        btnGoToWeb?.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }

        // Grid 3: Pindah ke Tab Profile di BottomNavigation
        btnGoToProfile?.setOnClickListener {
            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
            bottomNav?.selectedItemId = R.id.nav_profile
        }

        // Grid 4: Konfirmasi Logout
        btnLogoutUser?.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah kamu yakin ingin keluar dari aplikasi?")
                .setPositiveButton("Ya, Keluar") { _, _ ->
                    // Hapus semua sesi login yang tersimpan
                    requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                        .edit().clear().apply()
                    requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                        .edit().clear().apply()

                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}