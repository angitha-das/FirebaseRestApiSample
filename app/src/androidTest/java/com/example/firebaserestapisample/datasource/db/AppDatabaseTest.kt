package com.example.firebaserestapisample.datasource.db

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firebaserestapisample.model.User
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest{

    companion object {
        private var userDao: UserDao? = null
        @BeforeClass @JvmStatic
        fun initDb() {
            userDao = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase::class.java
            ).build().userDao()
        }
    }


    @Test
    fun testA_should_Insert_User_Item() {
        val user = User("John","john@gmail.com","1212121212")
        userDao?.let {
            val rowCount = it.insertUser(user)
            assertEquals(1, rowCount)
        }
    }

    @Test
    fun testB_should_Return_User_Items() {
        userDao?.let {
            val rows = it.getAllUsers()
            assertEquals(1, rows.size)
            assertEquals(rows[0].name, "John")
        }
    }
}