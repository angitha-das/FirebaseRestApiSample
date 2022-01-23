package com.example.firebaserestapisample.datasource.network

import com.example.firebaserestapisample.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    /**
     * Get list of users.
     */
    @GET("/users.json")
    fun  getAllUsers(): Call<List<User>>
}