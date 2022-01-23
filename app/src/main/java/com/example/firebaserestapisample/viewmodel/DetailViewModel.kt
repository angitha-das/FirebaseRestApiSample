package com.example.firebaserestapisample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.firebaserestapisample.datasource.DataRequestState
import com.example.firebaserestapisample.datasource.db.AppDatabase
import com.example.firebaserestapisample.datasource.network.ApiClient
import com.example.firebaserestapisample.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private var db: AppDatabase = AppDatabase.getInstance(application.applicationContext)
    var dataRequestState: MutableLiveData<DataRequestState> = MutableLiveData()

    fun saveUserToFirebase(user: User){
        dataRequestState.postValue(DataRequestState.LOADING)
        ApiClient.getInstance().apiService.insertUser(user).enqueue(object : Callback<Response<String>> {
            override fun onResponse(call: Call<Response<String>>, response: Response<Response<String>>) {
                if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_OK) {
                    db.userDao().insertUser(user)
                    dataRequestState.postValue(DataRequestState.LOADED)
                } else {
                    dataRequestState.postValue(DataRequestState(DataRequestState.Status.FAILED))
                }
            }

            override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                dataRequestState.postValue(DataRequestState(DataRequestState.Status.FAILED))
            }
        })
    }
}