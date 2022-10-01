package com.example.noteapp.extensions.extensions

import android.content.Context
import androidx.lifecycle.MutableLiveData


fun ActivityViewModel.getName(): MutableLiveData<String> {
    return this.name
}