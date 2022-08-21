package com.example.budgetbuddy

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.DecimalFormat
import java.util.*

abstract class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
        internal lateinit var sharedPref: SharedPreferences
        val cal: Calendar = Calendar.getInstance()
        var decimalFormat: DecimalFormat = DecimalFormat("#,###.##")

    }

    fun <B> LogStr(param: B) {
        Log.d(TAG, param.toString())
    }

    fun <T> showShortToastMessage(param: T) {
        Toast.makeText(context, param.toString(), Toast.LENGTH_SHORT).show()
    }

    fun <A> showLongToastMessage(param: A) {
        Toast.makeText(context, param.toString(), Toast.LENGTH_LONG).show()
    }
}