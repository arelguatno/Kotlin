package com.example.budgetbuddy.screens.search_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.databinding.SearchParentBinding
import com.example.budgetbuddy.room.tables.TransactionList
import com.example.budgetbuddy.screens.transactions_screen.TransactionFragmentAdapterChild
import com.example.budgetbuddy.utils.dateToNice

class SearchFragmentAdapterHeader :
    ListAdapter<TransactionList, SearchFragmentAdapterHeader.MyViewHolder>(WORDS_COMPARATOR) {

    class MyViewHolder(val binding: SearchParentBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<TransactionList>() {
            override fun areItemsTheSame(
                oldItem: TransactionList,
                newItem: TransactionList
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: TransactionList,
                newItem: TransactionList
            ): Boolean {
                return oldItem.header == newItem.header
                return oldItem.child == newItem.child
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SearchParentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.date.text = dateToNice(item.header)

        val child = TransactionFragmentAdapterChild(item.child.reversed(), true)
        holder.binding.rvChild.layoutManager = LinearLayoutManager(
            holder.binding.rvChild.context,
            LinearLayoutManager.VERTICAL,
            false
        )
         holder.binding.rvChild.adapter = child
    }
}


