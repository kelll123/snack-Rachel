package com.example.snack_rachel.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.snack_rachel.LoginActivity
import com.example.snack_rachel.R
import com.google.android.material.button.MaterialButton

class Onboard3Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboard_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnMulai = view.findViewById<MaterialButton>(R.id.btnMulaiSekarang)
        btnMulai.setOnClickListener {
            // Simpan status bahwa user sudah melihat Onboard Screen
            val sharedPref = requireContext().getSharedPreferences("OnboardingPrefs", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("isCompleted", true).apply()

            // Pindah ke LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}