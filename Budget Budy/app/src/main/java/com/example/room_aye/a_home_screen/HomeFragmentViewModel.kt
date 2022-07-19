package com.example.room_aye.a_home_screen

import androidx.lifecycle.*
import com.example.room_aye.room.TransactionsRepository
import com.example.room_aye.room.TransactionsTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: TransactionsRepository
) : ViewModel(), LifecycleObserver {

    val fetchAllProfile = repository.fetchAllProfile().asLiveData()

    fun insertProfileRecord(transactionsTable: TransactionsTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProfileRecord(transactionsTable)
        }
    }
}