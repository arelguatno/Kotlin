package com.example.timer

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        initViewModels()
    }

    private fun initViewModels() {
        viewModel.getTime().observe(this, Observer {
            val textView = findViewById<TextView>(R.id.TextView)
            textView.text = it.toString()


            if (viewModel.getSnackBackTimer() == it) {
                val snack = Snackbar.make(
                    textView,
                    "Rotate the phone, and timer should persists",
                    Snackbar.LENGTH_LONG
                )
                snack.show()
            }
        })

        viewModel.timerIsDone().observe(this, Observer {
            if (it == true) Toast.makeText(this, "Time's up", Toast.LENGTH_SHORT).show()
        })
    }

    fun startTimerOnClick(view: View) {
        viewModel.startTimer()

    }
}