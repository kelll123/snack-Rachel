package com.example.snack_rachel.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SupabaseClient {
    private const val BASE_URL = "https://GANTI_PROJECT_ID_KAMU.supabase.co/rest/v1/"
    private const val API_KEY = "GANTI_ANON_PUBLIC_API_KEY_KAMU"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer $API_KEY")
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=minimal")
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: SupabaseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SupabaseApiService::class.java)
    }
}