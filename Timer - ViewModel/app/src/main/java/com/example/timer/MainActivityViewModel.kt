package com.example.timer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class MainActivityViewModel : ViewModel() {
    private lateinit var timer: CountDownTimer

    private val tenSeconds: Long = 11000
    private val interval: Long = 1000

    private var perSecond = MutableLiveData<Int>()
    private var timerIsDone = MutableLiveData<Boolean>()
    private var isRunning = false

    fun startTimer() {
        if (!isRunning) {
            timer = object : CountDownTimer(tenSeconds, interval) {
                override fun onTick(value: Long) {
                    val timeLeft = value / 1000
                    perSecond.value = timeLeft.toInt()
                    isRunning = true
                }

                override fun onFinish() {
                    isRunning = false
                    timerIsDone.value = true
                }
            }.start()
        }
    }

    fun timerIsDone(): LiveData<Boolean> {
        return timerIsDone
    }

    fun getTime(): LiveData<Int> {
        return perSecond
    }
}