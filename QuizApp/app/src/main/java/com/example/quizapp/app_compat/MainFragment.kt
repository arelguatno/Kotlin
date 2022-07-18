package com.example.quizapp.app_compat

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

open class MainFragment: Fragment() {

    companion object{
        const val TAG = "QuizApp"
    }

    fun<T> console(msg: T) {
        Log.d(TAG, "$msg")
    }

    fun<A> showToastMessageLong(msg: A){
        Toast.makeText(context,msg.toString(), Toast.LENGTH_LONG).show()
    }

    fun<B> showToastMessageShort(msg: B){
        Toast.makeText(context,msg.toString(), Toast.LENGTH_SHORT).show()
    }
}