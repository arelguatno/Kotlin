package com.example.budgetbuddy.module

import android.app.Application
import android.os.Build
import android.view.Window
import android.view.WindowManager
import com.example.budgetbuddy.R
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}