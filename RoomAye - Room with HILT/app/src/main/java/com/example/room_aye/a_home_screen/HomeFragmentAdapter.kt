package com.example.room_aye.a_home_screen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room_aye.R
import com.example.room_aye.a_home_screen.HomeFragmentAdapter.WordViewHolder
import com.example.room_aye.room.TransactionsTable

class HomeFragmentAdapter : ListAdapter<TransactionsTable, WordViewHolder>(WORDS_COMPARATOR) {

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
        private val txtCostPrice: TextView = itemView.findViewById(R.id.txtCostPrice)
        private val txtNote: TextView = itemView.findViewById(R.id.txtNote)

        fun bind(category: String?, amount: Double?, note: String?, date: String?) {
            txtCategory.text = category
            txtCostPrice.text = amount.toString()
            txtNote.text = note

            txtNote.setOnClickListener{
                Log.d("Aye", it.toString())
            }
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.transactions_custom_row, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<TransactionsTable>() {
            override fun areItemsTheSame(oldItem: TransactionsTable, newItem: TransactionsTable): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: TransactionsTable, newItem: TransactionsTable): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            category = current.cateogry,
            amount = current.amount,
            note = current.note,
            date = ""
        )
    }
}