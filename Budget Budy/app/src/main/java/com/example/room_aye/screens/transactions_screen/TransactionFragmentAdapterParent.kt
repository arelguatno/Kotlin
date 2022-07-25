package com.example.room_aye.screens.transactions_screen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room_aye.databinding.TransactionsParentBinding
import com.example.room_aye.room.tables.TransactionList
import com.example.room_aye.utils.intDayToString
import com.example.room_aye.utils.intMonthToString
import java.util.*

class TransactionFragmentAdapterParent :
    ListAdapter<TransactionList, TransactionFragmentAdapterParent.MyViewHolder>(WORDS_COMPARATOR) {

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
        val current = getItem(position)

        val cal = Calendar.getInstance()
        cal.time = Date(current.header!!)

        holder.binding.dayOfMonth.text = cal.get(Calendar.DAY_OF_MONTH).toString()
        Log.d("aye", current.header + " " + cal.get(Calendar.DAY_OF_WEEK).toString() )
        holder.binding.dayOfWeek.text = intDayToString(cal.get(Calendar.DAY_OF_WEEK))
        holder.binding.monthAndYear.text = "${intMonthToString(cal.get(Calendar.MONTH))} ${cal.get(Calendar.YEAR)}"

        var total: Double = 0.0
         for(i in current.child){
             total += i.amount
        }
        holder.binding.total.text = String.format("%.2f", total)

        val child = TransactionFragmentAdapterChild(current.child)
        holder.binding.rvChild.layoutManager = LinearLayoutManager(
            holder.binding.rvChild.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        holder.binding.rvChild.adapter = child
    }
}

