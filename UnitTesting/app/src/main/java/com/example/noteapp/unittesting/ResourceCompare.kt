package com.example.noteapp.unittesting

import android.content.Context

class ResourceCompare {

    fun isEqual(context:Context, id: Int, string:String):Boolean{
        return context.getString(id) == string
    }
}