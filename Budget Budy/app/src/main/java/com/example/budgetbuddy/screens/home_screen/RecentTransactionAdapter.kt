package com.example.budgetbuddy.screens.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.databinding.RecentTransactionsBinding
import com.example.budgetbuddy.databinding.TransactionsCustomRowBinding
import com.example.budgetbuddy.databinding.TransactionsParentBinding
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.room.tables.TransactionList
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.screens.transactions_screen.TransactionFragmentDirections
import com.example.budgetbuddy.utils.dateToNice
import com.example.budgetbuddy.utils.intDayToString
import com.example.budgetbuddy.utils.intMonthLongToString
import com.example.budgetbuddy.utils.transformSingleDigitToTwoDigit
import java.util.*

class RecentTransactionAdapter :
    ListAdapter<TransactionsTable, RecentTransactionAdapter.MyViewHolder>(WORDS_COMPARATOR) {

    class MyViewHolder(val binding: TransactionsCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<TransactionsTable>() {
            override fun areItemsTheSame(
                oldItem: TransactionsTable,
                newItem: TransactionsTable
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: TransactionsTable,
                newItem: TransactionsTable
            ): Boolean {
                return oldItem.date == newItem.date
                return oldItem.category == newItem.category
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            TransactionsCustomRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)

        if (item.catAmount > 0.0) {
            holder.binding.txtCostPrice.text = "${item.percentage.toInt()}%"
            holder.binding.txtNote.text =
                String.format("${item.currency.textIcon} %.2f", item.catAmount)
        } else {
            holder.binding.txtCostPrice.text =
                String.format("-${item.currency.textIcon} %.2f", item.amount)
            holder.binding.txtNote.text = dateToNice(item.date)
        }

        holder.binding.txtCategory.text = item.category.rowValue
        holder.binding.imageView.setImageResource(CategoryList.getImageID(item.category.uniqueID)!!)

    }
}


