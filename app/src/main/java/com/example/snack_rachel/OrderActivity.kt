package com.example.snack_rachel

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snack_rachel.data.api.SupabaseClient
import com.example.snack_rachel.data.model.OrderSupabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class OrderActivity : AppCompatActivity() {

    private lateinit var adapter: OrderAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        progressBar = findViewById(R.id.progressOrder)
        val rvOrders = findViewById<RecyclerView>(R.id.rvOrders)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAddOrder)
        val btnBack = findViewById<ImageButton>(R.id.btnBackOrder)

        btnBack.setOnClickListener { finish() }

        adapter = OrderAdapter(
            listOrder = emptyList(),
            onEditClick = { order -> showOrderDialog(order) },
            onDeleteClick = { order -> confirmDelete(order) }
        )

        rvOrders.layoutManager = LinearLayoutManager(this)
        rvOrders.adapter = adapter

        fabAdd.setOnClickListener { showOrderDialog(null) }

        loadOrders()
    }

    // READ (GET)
    private fun loadOrders() {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val data = SupabaseClient.apiService.getOrders()
                adapter.updateData(data)
            } catch (e: Exception) {
                Toast.makeText(this@OrderActivity, "Gagal memuat pesanan: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    // CREATE (POST) & UPDATE (PATCH)
    private fun showOrderDialog(existingOrder: OrderSupabase?) {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 12)
        }

        val etPemesan = EditText(this).apply {
            hint = "Nama Pemesan"
            setText(existingOrder?.nama_pemesan ?: "")
        }
        val etMenu = EditText(this).apply {
            hint = "Menu Mie Ayam yang Dipesan"
            setText(existingOrder?.menu_dipesan ?: "")
        }
        val etJumlah = EditText(this).apply {
            hint = "Jumlah Porsi (angka)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setText(existingOrder?.jumlah?.toString() ?: "1")
        }
        val etCatatan = EditText(this).apply {
            hint = "Catatan (opsional)"
            setText(existingOrder?.catatan ?: "")
        }

        layout.addView(etPemesan)
        layout.addView(etMenu)
        layout.addView(etJumlah)
        layout.addView(etCatatan)

        AlertDialog.Builder(this)
            .setTitle(if (existingOrder == null) "Tambah Pesanan Baru" else "Edit Pesanan")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val pemesan = etPemesan.text.toString().trim()
                val menu = etMenu.text.toString().trim()
                val jumlah = etJumlah.text.toString().toIntOrNull() ?: 1
                val catatan = etCatatan.text.toString().trim()

                if (pemesan.isEmpty() || menu.isEmpty()) {
                    Toast.makeText(this, "Nama pemesan & menu wajib diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val orderData = OrderSupabase(
                    nama_pemesan = pemesan,
                    menu_dipesan = menu,
                    jumlah = jumlah,
                    catatan = catatan
                )

                lifecycleScope.launch {
                    try {
                        val response = if (existingOrder == null) {
                            SupabaseClient.apiService.createOrder(orderData)
                        } else {
                            SupabaseClient.apiService.updateOrder("eq.${existingOrder.id}", orderData)
                        }

                        if (response.isSuccessful) {
                            Toast.makeText(this@OrderActivity, "Pesanan berhasil disimpan!", Toast.LENGTH_SHORT).show()
                            loadOrders()
                        } else {
                            Toast.makeText(this@OrderActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@OrderActivity, "Gagal menyimpan: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // DELETE
    private fun confirmDelete(order: OrderSupabase) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Pesanan")
            .setMessage("Yakin ingin menghapus pesanan milik ${order.nama_pemesan}?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    try {
                        val response = SupabaseClient.apiService.deleteOrder("eq.${order.id}")
                        if (response.isSuccessful) {
                            Toast.makeText(this@OrderActivity, "Pesanan dihapus!", Toast.LENGTH_SHORT).show()
                            loadOrders()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@OrderActivity, "Gagal menghapus: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}