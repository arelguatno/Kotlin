package com.example.budgetbuddy.fragments.onboarding.splashscreen

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor() : ViewModel() {
    private val onFinished = MutableLiveData<Boolean>()
    private val delayMills: Long = 0 // 1 sec

    fun startTimer() {
        Handler(Looper.getMainLooper()).postDelayed({
            onFinished.value = true
        }, delayMills)
    }

    fun onFinished(): MutableLiveData<Boolean> {
        return onFinished
    }
}