package com.example.snack_rachel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : Fragment() {

    private lateinit var menuAdapter: MenuAdapter

    // Data List Menu Mie Ayam & Minuman
    private val daftarMenu = listOf(
        MenuModel("Mie Ayam Original", "Mie lezat dengan potongan daging ayam manis gurih", "Rp 12.000", R.drawable.mieayam),
        MenuModel("Mie Ayam Special Bakso", "Mie ayam komplit dengan 2 butir bakso sapi empuk", "Rp 16.000", R.drawable.mieayam),
        MenuModel("Mie Ayam Pangsit Goreng", "Mie ayam gurih dengan kerupuk pangsit renyah", "Rp 14.000", R.drawable.mieayam),
        MenuModel("Mie Ayam Ceker Pedas", "Mie ayam dengan topping ceker bumbu pedas manis", "Rp 15.000", R.drawable.mieayam),
        MenuModel("Mie Ayam Komplit Jumbo", "Porsi jumbo lengkap dengan bakso, pangsit, dan ceker", "Rp 20.000", R.drawable.mieayam),
        MenuModel("Es Teh Manis Segar", "Minuman pendamping makan mie ayam", "Rp 5.000", R.drawable.mieayam)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvMenu = view.findViewById<RecyclerView>(R.id.rvMenu)
        val searchMenu = view.findViewById<SearchView>(R.id.searchMenu)

        // Inisialisasi Custom Adapter
        menuAdapter = MenuAdapter(daftarMenu) { item ->
            Toast.makeText(requireContext(), "Memilih: ${item.nama} (${item.harga})", Toast.LENGTH_SHORT).show()
        }

        // Hubungkan RecyclerView dengan LayoutManager & Adapter
        rvMenu.layoutManager = LinearLayoutManager(requireContext())
        rvMenu.adapter = menuAdapter

        // Fitur Search tetap berjalan pada RecyclerView
        searchMenu?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().lowercase().trim()
                val filteredList = daftarMenu.filter {
                    it.nama.lowercase().contains(query) || it.deskripsi.lowercase().contains(query)
                }
                menuAdapter.updateData(filteredList)
                return true
            }
        })
    }
}