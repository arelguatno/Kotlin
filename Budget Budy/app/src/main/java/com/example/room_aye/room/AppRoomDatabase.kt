package com.example.room_aye.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.room_aye.room.tables.TransactionsTable
import com.example.room_aye.room.type_converter.DateConverter

@Database(
    entities = [TransactionsTable::class],
    version = 1,
    exportSchema = false
)@TypeConverters(DateConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun profileDao(): TransactionsDao

}