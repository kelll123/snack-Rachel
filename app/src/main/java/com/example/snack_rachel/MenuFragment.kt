package com.example.snack_rachel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchMenu = view.findViewById<SearchView>(R.id.searchMenu)
        val cardOriginal = view.findViewById<MaterialCardView>(R.id.cardMieOriginal)
        val cardBakso = view.findViewById<MaterialCardView>(R.id.cardMieBakso)
        val tvOriginal = view.findViewById<TextView>(R.id.tvTitleOriginal)
        val tvBakso = view.findViewById<TextView>(R.id.tvTitleBakso)

        searchMenu?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().lowercase().trim()

                // Logika Filter Pencarian
                val matchOriginal = tvOriginal?.text.toString().lowercase().contains(query)
                val matchBakso = tvBakso?.text.toString().lowercase().contains(query)

                cardOriginal?.visibility = if (matchOriginal) View.VISIBLE else View.GONE
                cardBakso?.visibility = if (matchBakso) View.VISIBLE else View.GONE

                return true
            }
        })
    }
}