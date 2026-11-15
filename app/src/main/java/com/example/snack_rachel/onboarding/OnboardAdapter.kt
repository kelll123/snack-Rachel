package com.example.snack_rachel.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Onboard1Fragment()
            1 -> Onboard2Fragment()
            2 -> Onboard3Fragment()
            else -> throw IllegalStateException("Posisi tab tidak valid")
        }
    }
}