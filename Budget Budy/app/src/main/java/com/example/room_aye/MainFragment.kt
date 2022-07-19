package com.example.room_aye

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

open class MainFragment: Fragment() {

    companion object{
        const val TAG = "MainFragment"
    }

    fun<B> LogStr(param: B){
        Log.d(TAG, param.toString())
    }

    fun<T> showShortToastMessage(param: T){
        Toast.makeText(context,param.toString(),Toast.LENGTH_SHORT).show()
    }

    fun<A> showLongToastMessage(param: A){
        Toast.makeText(context, param.toString(),Toast.LENGTH_LONG).show()
    }
}