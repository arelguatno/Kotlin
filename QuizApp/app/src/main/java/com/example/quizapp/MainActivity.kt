package com.example.quizapp

import android.os.Bundle
import androidx.navigation.findNavController
import com.example.quizapp.app_compat.MainAppCompatActivity

class MainActivity : MainAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}