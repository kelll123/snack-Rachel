package com.example.snack_rachel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private var menuList: List<MenuModel>,
    private val onItemClick: (MenuModel) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        holder.imgMenu.setImageResource(item.gambarResId)
        holder.tvNama.text = item.nama
        holder.tvDeskripsi.text = item.deskripsi
        holder.tvHarga.text = item.harga

        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = menuList.size

    fun updateData(newList: List<MenuModel>) {
        menuList = newList
        notifyDataSetChanged()
    }
}