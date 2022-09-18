package com.example.timer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class MainActivityViewModel : ViewModel() {
    private lateinit var timer: CountDownTimer

    private val lengthTimer: Long = 16 * 1000 // 16 secs
    private val interval: Long = 1 * 1000  // 1 sec

    private var perSecond = MutableLiveData<Int>()
    private var timerIsDone = MutableLiveData<Boolean>()
    private var isRunning = false

    fun startTimer() {
        if (!isRunning) {
            timer = object : CountDownTimer(lengthTimer, interval) {
                override fun onTick(value: Long) {
                    val timeLeft = value / 1000
                    perSecond.value = timeLeft.toInt()
                    isRunning = true
                }

                override fun onFinish() {
                    isRunning = false
                    timerIsDone.value = true
                    timerIsDone.value = false
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

    fun getSnackBackTimer(): Int{
        return (((lengthTimer / interval) / 1.4).toInt())
    }
}