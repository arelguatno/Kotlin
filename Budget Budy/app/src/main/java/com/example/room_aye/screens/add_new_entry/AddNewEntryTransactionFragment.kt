package com.example.room_aye.screens.add_new_entry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_aye.databinding.FragmentAddNewEntryTransactionBinding

class AddNewEntryTransactionFragment : Fragment() {

    companion object {
    }

    private lateinit var binding: FragmentAddNewEntryTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewEntryTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadRowItems()
    }

    private fun loadRowItems() {
        val items = AddNewTransactionActivityAdapter(NewEntryRowArrayList.getRowListItems())
        binding.items.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.items.adapter = items
    }
}