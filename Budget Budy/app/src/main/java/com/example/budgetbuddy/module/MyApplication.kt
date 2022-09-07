package com.example.budgetbuddy.module

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

//        val context: Context = LocaleHelper.setLocale(this, "en");
//        val resources: Resources = context.resources;
//        messageView.setText(resources.getString(R.string.language));
    }
}