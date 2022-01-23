package com.example.firebaserestapisample.datasource.network

import com.example.firebaserestapisample.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    /**
     * Get list of users from firebase database.
     */
    @GET("/users.json")
    fun  getAllUsers(): Call<Map<String,User>>

    /**
     * Insert user to firebase database.
     */
    @POST("/users.json")
    fun  insertUser(@Body user: User): Call<Response<String>>
}