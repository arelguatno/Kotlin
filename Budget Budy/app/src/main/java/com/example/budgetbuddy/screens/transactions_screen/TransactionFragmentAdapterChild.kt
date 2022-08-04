package com.example.budgetbuddy.screens.transactions_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.TransactionsCustomRowBinding
import com.example.budgetbuddy.room.tables.TransactionsTable


class TransactionFragmentAdapterChild(private val children: List<TransactionsTable>) :
    RecyclerView.Adapter<TransactionFragmentAdapterChild.MyViewHolder>() {

    class MyViewHolder(val binding: TransactionsCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            TransactionsCustomRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = children[position]

        holder.binding.txtCategory.text = item.categoryValue

        holder.binding.txtCostPrice.text = String.format("-$ %.2f", item.amount)
        holder.binding.imageView.setImageResource(R.drawable.ic_baseline_directions_bus_24)
    }

}