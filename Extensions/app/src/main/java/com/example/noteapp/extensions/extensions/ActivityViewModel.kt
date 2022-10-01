package com.example.noteapp.extensions.extensions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ActivityViewModel : ViewModel() {
    internal val name = MutableLiveData<String>()
}