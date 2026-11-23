package com.example.snack_rachel.data.model

data class UserRequest(
    val username: String,
    val password: String,
    val nama_lengkap: String
)

data class UserResponse(
    val id: Int? = null,
    val username: String,
    val password: String,
    val nama_lengkap: String
)

data class MenuSupabase(
    val id: Int? = null,
    val nama_menu: String,
    val harga: Int,
    val deskripsi: String
)

data class OrderSupabase(
    val id: Int? = null,
    val nama_pemesan: String,
    val menu_dipesan: String,
    val jumlah: Int,
    val catatan: String
)