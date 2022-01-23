package com.example.firebaserestapisample.datasource.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    var apiService: ApiService
    init {

        val logger = HttpLoggingInterceptor() //For API Logging
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://fir-restapisample-e3fc7-default-rtdb.firebaseio.com"
        private val apiClient: ApiClient = ApiClient()
        @Synchronized
        fun getInstance(): ApiClient {
            return apiClient
        }
    }
}