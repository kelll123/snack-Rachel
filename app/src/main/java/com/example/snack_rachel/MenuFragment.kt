package com.example.snack_rachel

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snack_rachel.data.api.SupabaseClient
import com.example.snack_rachel.data.model.MenuSupabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {

    private lateinit var adapter: MenuAdapter
    private lateinit var progressBar: ProgressBar
    private var fullMenuList: List<MenuSupabase> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressMenu)
        val rvMenu = view.findViewById<RecyclerView>(R.id.rvMenu)
        val searchView = view.findViewById<SearchView>(R.id.searchViewMenu)
        val fabAddMenu = view.findViewById<FloatingActionButton>(R.id.fabAddMenu)

        adapter = MenuAdapter(
            menuList = emptyList(),
            onItemClick = { menu -> showMenuDialog(menu) },
            onItemLongClick = { menu -> confirmDeleteMenu(menu) }
        )

        rvMenu.layoutManager = LinearLayoutManager(requireContext())
        rvMenu.adapter = adapter

        fabAddMenu.setOnClickListener { showMenuDialog(null) }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val keyword = newText.orEmpty().lowercase()
                val filtered = fullMenuList.filter {
                    it.nama_menu.lowercase().contains(keyword) || it.deskripsi.lowercase().contains(keyword)
                }
                adapter.updateData(filtered)
                return true
            }
        })

        loadMenus()
    }

    // READ (GET)
    private fun loadMenus() {
        progressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                fullMenuList = SupabaseClient.apiService.getMenus()
                adapter.updateData(fullMenuList)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal memuat menu: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    // CREATE (POST) & UPDATE (PATCH)
    private fun showMenuDialog(existingMenu: MenuSupabase?) {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 12)
        }

        val etNama = EditText(requireContext()).apply {
            hint = "Nama Menu (cth: Mie Ayam Bakso)"
            setText(existingMenu?.nama_menu ?: "")
        }
        val etHarga = EditText(requireContext()).apply {
            hint = "Harga (cth: 15000)"
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(existingMenu?.harga?.toString() ?: "")
        }
        val etDeskripsi = EditText(requireContext()).apply {
            hint = "Deskripsi Menu"
            setText(existingMenu?.deskripsi ?: "")
        }

        layout.addView(etNama)
        layout.addView(etHarga)
        layout.addView(etDeskripsi)

        AlertDialog.Builder(requireContext())
            .setTitle(if (existingMenu == null) "Tambah Menu Baru" else "Edit Menu")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val nama = etNama.text.toString().trim()
                val harga = etHarga.text.toString().toIntOrNull() ?: 0
                val deskripsi = etDeskripsi.text.toString().trim()

                if (nama.isEmpty() || harga <= 0) {
                    Toast.makeText(requireContext(), "Nama menu dan harga wajib diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val menuData = MenuSupabase(
                    nama_menu = nama,
                    harga = harga,
                    deskripsi = deskripsi
                )

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val response = if (existingMenu == null) {
                            SupabaseClient.apiService.createMenu(menuData)
                        } else {
                            SupabaseClient.apiService.updateMenu("eq.${existingMenu.id}", menuData)
                        }

                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Menu berhasil disimpan!", Toast.LENGTH_SHORT).show()
                            loadMenus()
                        } else {
                            Toast.makeText(requireContext(), "Gagal (${response.code()})", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // DELETE
    private fun confirmDeleteMenu(menu: MenuSupabase) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Menu")
            .setMessage("Hapus '${menu.nama_menu}' dari daftar menu?")
            .setPositiveButton("Hapus") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val response = SupabaseClient.apiService.deleteMenu("eq.${menu.id}")
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Menu berhasil dihapus!", Toast.LENGTH_SHORT).show()
                            loadMenus()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Gagal menghapus: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}