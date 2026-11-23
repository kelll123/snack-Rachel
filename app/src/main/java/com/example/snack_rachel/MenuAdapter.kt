package com.example.snack_rachel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snack_rachel.data.model.MenuSupabase
import java.text.NumberFormat
import java.util.Locale

class MenuAdapter(
    private var menuList: List<MenuSupabase>,
    private val onItemClick: (MenuSupabase) -> Unit,
    private val onItemLongClick: (MenuSupabase) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMenu: ImageView = itemView.findViewById(R.id.imgMenu)
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaMenu)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsiMenu)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHargaMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList[position]
        val formatRp = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

        holder.imgMenu.setImageResource(R.drawable.mieayam)
        holder.tvNama.text = item.nama_menu
        holder.tvDeskripsi.text = item.deskripsi
        holder.tvHarga.text = formatRp.format(item.harga)

        // Klik biasa -> Edit Menu (UPDATE)
        holder.itemView.setOnClickListener { onItemClick(item) }

        // Tekan lama -> Hapus Menu (DELETE)
        holder.itemView.setOnLongClickListener {
            onItemLongClick(item)
            true
        }
    }

    override fun getItemCount(): Int = menuList.size

    fun updateData(newList: List<MenuSupabase>) {
        menuList = newList
        notifyDataSetChanged()
    }
}