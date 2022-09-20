package eu.tutorials.ageinminutes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class MainViewModel: ViewModel() {
    var selectedDate = MutableLiveData<String>()
    var ageinMinutes = MutableLiveData<String>()

    fun getDate(): LiveData<String>{
        return selectedDate
    }

    fun getMinutes(): LiveData<String>{
        return ageinMinutes
    }
}