package com.example.timer

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.startTimer()

        viewModel.getTime().observe(this, Observer {
            val textView = findViewById<TextView>(R.id.TextView)
            textView.text = it.toString()

        })

        viewModel.timerIsDone().observe(this, Observer {
            Toast.makeText(this,"Time's up", Toast.LENGTH_SHORT).show()
        })

    }
}