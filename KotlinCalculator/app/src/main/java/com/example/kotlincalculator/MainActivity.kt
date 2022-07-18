package com.example.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.kotlincalculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        var last_numeric = false
        var last_dot = false
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }

    fun onDigit(view: View) {
        binding.tvInput?.append((view as Button).text)
        last_numeric = true
        last_dot = false
    }


    fun onDecimalPoint(view: View) {
        if (last_numeric && !last_dot) {
            binding.tvInput?.append(".")

            last_numeric = false
            last_dot = true
        }
    }

    fun onOperator(view: View) {
        binding.tvInput?.text?.let {
            if (last_numeric && !isOperatorAdded(it.toString())) {
                binding.tvInput?.append((view as Button).text)
            }
        }
    }

    fun onEqual(view: View) {
        if (last_numeric) {
            var tvValue = binding?.tvInput.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    binding.tvInput?.text = (one.toInt() - two.toInt()).toString()
                }else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    binding.tvInput?.text = (one.toInt() + two.toInt()).toString()
                }else if(tvValue.contains("÷")) {
                    val splitValue = tvValue.split("÷")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    binding.tvInput?.text = (one.toInt() / two.toInt()).toString()
                }else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    binding.tvInput?.text = (one.toInt() * two.toInt()).toString()
                }

            } catch (e: ArithmeticException) {
                Log.d(TAG, e.printStackTrace().toString())
            }
        }
    }

    fun onClear(view: View) {
        binding.tvInput?.text = ""
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}