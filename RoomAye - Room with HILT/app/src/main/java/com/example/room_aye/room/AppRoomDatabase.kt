package com.example.room_aye.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room_aye.room.TransactionsTable
import com.example.room_aye.room.TransactionsDao

@Database(
    entities = [TransactionsTable::class],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun profileDao(): TransactionsDao

}