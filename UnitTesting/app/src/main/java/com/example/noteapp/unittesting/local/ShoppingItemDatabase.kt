package com.example.noteapp.unittesting.local

import ShoppingItem
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.unittesting.local.ShoppingDao

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}