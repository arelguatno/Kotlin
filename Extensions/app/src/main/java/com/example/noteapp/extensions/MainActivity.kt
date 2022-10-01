package com.example.noteapp.extensions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.extensions.extensions.ActivityViewModel
import com.example.noteapp.extensions.extensions.getName
import com.example.noteapp.extensions.extensions.setName

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this@MainActivity)[ActivityViewModel::class.java]
        //Usage
        val a: String = "Hey there"
        val words = a.getAllWords()

        viewModel.setName("arel guatno")
    }


    //Basic Extension
    fun String.getAllWords(): List<String> {
        return this.split(" ")
    }
}