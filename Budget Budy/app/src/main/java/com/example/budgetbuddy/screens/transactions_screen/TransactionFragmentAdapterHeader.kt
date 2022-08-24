package com.example.budgetbuddy.screens.transactions_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.databinding.TransactionsParentBinding
import com.example.budgetbuddy.room.tables.TransactionList
import com.example.budgetbuddy.utils.intDayToString
import com.example.budgetbuddy.utils.intMonthLongToString
import com.example.budgetbuddy.utils.transformSingleDigitToTwoDigit
import java.util.*

class TransactionFragmentAdapterHeader :
    ListAdapter<TransactionList, TransactionFragmentAdapterHeader.MyViewHolder>(WORDS_COMPARATOR) {

    class MyViewHolder(val binding: TransactionsParentBinding) :
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
            TransactionsParentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)

        val cal = Calendar.getInstance()
        cal.time = item.header

        if (position != 0) {
            //holder.binding.haha.isVisible = false
        }

        holder.binding.dayOfMonth.text =
            transformSingleDigitToTwoDigit(cal.get(Calendar.DAY_OF_MONTH))
        holder.binding.dayOfWeek.text = intDayToString(cal.get(Calendar.DAY_OF_WEEK))
        holder.binding.monthAndYear.text = "${intMonthLongToString(cal.get(Calendar.MONTH))} ${cal.get(Calendar.YEAR)}"
        holder.binding.total.text = item.child[item.child.lastIndex].labels!!.headerLabel

        val child = TransactionFragmentAdapterChild(item.child.reversed(), false)
        holder.binding.rvChild.layoutManager = LinearLayoutManager(
            holder.binding.rvChild.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        holder.binding.rvChild.adapter = child
    }
}


