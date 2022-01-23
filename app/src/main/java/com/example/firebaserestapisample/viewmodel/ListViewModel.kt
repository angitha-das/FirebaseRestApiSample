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

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private var db: AppDatabase = AppDatabase.getInstance(application.applicationContext)
    var dataRequestState: MutableLiveData<DataRequestState> = MutableLiveData()
    var usersLiveData: MutableLiveData<List<User>> = MutableLiveData()

    init {
        fetchAllUsersFromFirebase()
    }

    private fun fetchAllUsersFromFirebase() {
        dataRequestState.postValue(DataRequestState.LOADING)
        ApiClient.getInstance().apiService.getAllUsers().enqueue(object : Callback<Map<String,User>> {
            override fun onResponse(call: Call<Map<String,User>>, response: Response<Map<String,User>>) {
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let { users ->
                      db.userDao().insertAll(users.values)
                    }
                    dataRequestState.postValue(DataRequestState.LOADED)
                } else {
                    dataRequestState.postValue(DataRequestState(DataRequestState.Status.FAILED))
                }
            }

            override fun onFailure(call: Call<Map<String,User>>, t: Throwable) {
                dataRequestState.postValue(DataRequestState(DataRequestState.Status.FAILED))
            }
        })
    }

    fun fetchUsersFromDB() {
        val userDao = db.userDao()
        usersLiveData.postValue(userDao.getAllUsers())
    }

}