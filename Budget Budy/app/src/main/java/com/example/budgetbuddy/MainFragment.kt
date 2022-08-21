package com.example.budgetbuddy

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.DecimalFormat
import java.util.*

abstract class MainFragment : Fragment() {

    companion object {
        internal lateinit var sharedPref: SharedPreferences
        const val TAG = "MainFragment"
        val cal: Calendar = Calendar.getInstance()
    }

   // val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//    fun getCurrency(): Int {
//        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
//        return sharedPref.getInt(getString(R.string.PREFERENCE_CURRENCY_ID), 1)
//    }

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