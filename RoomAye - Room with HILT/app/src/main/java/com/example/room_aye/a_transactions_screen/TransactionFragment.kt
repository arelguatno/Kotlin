package com.example.room_aye.a_transactions_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.room_aye.R
import com.example.room_aye.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : MainFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    companion object {

    }
}