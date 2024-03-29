package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.example.roomapp.data.UserDao
import com.example.roomapp.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    val getAllPaged: PagingSource<Int, User> = userDao.getAllPaged()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<User>> {
        return userDao.searchData(searchQuery)
    }
}