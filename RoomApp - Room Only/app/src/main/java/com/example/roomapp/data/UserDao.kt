package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.roomapp.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_Table WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
    fun searchData(searchQuery: String): LiveData<List<User>>

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getAllPaged(): PagingSource<Int, User>
}