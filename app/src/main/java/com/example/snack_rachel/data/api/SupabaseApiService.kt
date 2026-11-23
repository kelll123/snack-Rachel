package com.example.snack_rachel.data.api

import com.example.snack_rachel.data.model.MenuSupabase
import com.example.snack_rachel.data.model.OrderSupabase
import com.example.snack_rachel.data.model.UserRequest
import com.example.snack_rachel.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseApiService {

    // 1. TABEL USERS (Register & Login)
    @POST("users")
    suspend fun registerUser(@Body user: UserRequest): Response<Unit>

    @GET("users?select=*")
    suspend fun getUsers(): List<UserResponse>

    // 2. TABEL MENUS (CRUD 1)
    @GET("menus?select=*&order=id.asc")
    suspend fun getMenus(): List<MenuSupabase>

    @POST("menus")
    suspend fun createMenu(@Body menu: MenuSupabase): Response<Unit>

    @PATCH("menus")
    suspend fun updateMenu(
        @Query("id") idFilter: String,
        @Body menu: MenuSupabase
    ): Response<Unit>

    @DELETE("menus")
    suspend fun deleteMenu(@Query("id") idFilter: String): Response<Unit>

    // 3. TABEL ORDERS (CRUD 2)
    @GET("orders?select=*&order=id.desc")
    suspend fun getOrders(): List<OrderSupabase>

    @POST("orders")
    suspend fun createOrder(@Body order: OrderSupabase): Response<Unit>

    @PATCH("orders")
    suspend fun updateOrder(
        @Query("id") idFilter: String,
        @Body order: OrderSupabase
    ): Response<Unit>

    @DELETE("orders")
    suspend fun deleteOrder(@Query("id") idFilter: String): Response<Unit>
}