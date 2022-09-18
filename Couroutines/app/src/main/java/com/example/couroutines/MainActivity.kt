package com.example.couroutines

import android.app.UiModeManager.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val scope =
        CoroutineScope(Dispatchers.IO + CoroutineName("MyScope")) // naming your own CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRadioButtons()
    }

    override fun onStart() {
        super.onStart()

        Thread.sleep(1000L)

        task1()

        // Easy way to call couroutine without creating variable
        // WARNING: GlobalScope is highly discouraged to use, because it will run (non stop) as long as your app does. Instead your lifecycleScope
        GlobalScope.launch {
            task2()
        }

        // This scope will be cancelled when the Activity/Fragment Lifecycle is destroyed.
        // Use lifecycleScope or viewmodelScope as much as possible
        lifecycleScope.launch {
            task2()
        }

        //Custom scope
        scope.launch {
            println(this.coroutineContext.toString())
            println("1")
            launch {
                delay(2000L)
                println("2")
            }
            launch {
                delay(1000L)
                println("3")
            }
        }
    }

    fun task1() {
        println("Hello from: " + Thread.currentThread().name)
    }

    suspend fun task2() {
        withContext(Dispatchers.IO) {
            delay(1000L)
            println("World from: " + Thread.currentThread().name)
        }

    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.cBOne -> {
                    if (checked) {
                        Toast.makeText(this, "${view.text} is checked" , Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "${view.text} is unChecked" , Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.cBTWo -> {
                    if (checked) {
                        Toast.makeText(this, x"${view.text} is checked" , Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "${view.text} is unChecked" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initRadioButtons() {
        val radioGroup = findViewById<RadioGroup>(R.id.mainRadio)
        radioGroup.setOnCheckedChangeListener { _, i ->
            val radio: RadioButton = findViewById(i)
            when (i) {
                R.id.rbOne -> {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.rbTwo -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                R.id.rbThree -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }

        }
    }
}