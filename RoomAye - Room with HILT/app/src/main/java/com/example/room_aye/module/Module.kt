package com.example.room_aye.module

import android.content.Context
import androidx.room.Room
import com.example.room_aye.room.AppRoomDatabase
import com.example.room_aye.room.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppRoomDatabase::class.java,
        "com.example.room_aye.db"
    ).build()

    @Provides
    fun provideChannelDao(appDatabase: AppRoomDatabase): TransactionsDao {
        return appDatabase.profileDao()
    }
}