package com.example.budgetbuddy.screens.search_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.budgetbuddy.BaseActivity
import com.example.budgetbuddy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}