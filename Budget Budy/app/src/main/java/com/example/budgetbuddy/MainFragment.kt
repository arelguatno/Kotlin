package com.example.budgetbuddy

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
abstract class MainFragment : Fragment() {
    @Inject lateinit var digitsConverter: DigitsConverter

    companion object {
        internal lateinit var sharedPref: SharedPreferences
        const val TAG = "MainFragment"
        val cal: Calendar = Calendar.getInstance()
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