package com.example.budgetbuddy.module

import android.content.Context
import androidx.room.Room
import com.example.budgetbuddy.room.AppRoomDatabase
import com.example.budgetbuddy.room.transactions_table.TransactionsDao
import com.example.budgetbuddy.DigitsConverter
import com.example.budgetbuddy.room.wallet_table.WalletsDao
import com.example.budgetbuddy.screens.settings_screen.SettingsFragmentViewModel
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
        "com.example.budgetbuddy.aye_db"
    ).build()

    @Provides
    fun provideChannelDao(appDatabase: AppRoomDatabase): TransactionsDao {
        return appDatabase.profileDao()
    }

    @Provides
    fun provideWalletDao(appDatabase: AppRoomDatabase): WalletsDao {
        return appDatabase.walletDao()
    }

    @Singleton
    @Provides
    fun provideNumberFormatOrigin(@ApplicationContext context: Context): DigitsConverter {
        return DigitsConverter(context)
    }

    @Provides
    fun provideBilling(@ApplicationContext context: Context): SettingsFragmentViewModel {
        return SettingsFragmentViewModel(context)
    }


}