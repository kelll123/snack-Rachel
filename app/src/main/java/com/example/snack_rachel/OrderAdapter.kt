package com.example.snack_rachel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snack_rachel.data.model.OrderSupabase

class OrderAdapter(
    private var listOrder: List<OrderSupabase>,
    private val onEditClick: (OrderSupabase) -> Unit,
    private val onDeleteClick: (OrderSupabase) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMenu: TextView = itemView.findViewById(R.id.tvOrderMenu)
        val tvCustomer: TextView = itemView.findViewById(R.id.tvOrderCustomer)
        val tvNote: TextView = itemView.findViewById(R.id.tvOrderNote)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEditOrder)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = listOrder[position]
        holder.tvMenu.text = "${item.menu_dipesan} (x${item.jumlah})"
        holder.tvCustomer.text = "Pemesan: ${item.nama_pemesan}"
        holder.tvNote.text = "Catatan: ${item.catatan.ifEmpty { "-" }}"

        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnDelete.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = listOrder.size

    fun updateData(newList: List<OrderSupabase>) {
        listOrder = newList
        notifyDataSetChanged()
    }
}