package com.example.budgetbuddy.screens.transactions_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.databinding.TransactionsCustomRowBinding
import com.example.budgetbuddy.fragments.category.CategoryList
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

        holder.binding.txtCategory.text = item.category.rowValue
        if (item.catAmount > 0.0) {
            holder.binding.txtCostPrice.text =
                String.format("-${item.currency.textIcon} %.2f", item.catAmount)
            holder.binding.txtNote.text = ""
        } else {
            holder.binding.txtCostPrice.text =
                String.format("-${item.currency.textIcon} %.2f", item.amount)

            holder.itemView.setOnClickListener {
                val action =
                    TransactionFragmentDirections.actionTransactionFragmentToTransactionDetailsFragment(
                        item
                    )
                it.findNavController().navigate(action)
            }
            holder.binding.txtNote.text = item.note
        }

        holder.binding.imageView.setImageResource(CategoryList.getImageID(item.category.uniqueID)!!)
        //holder.binding.imageView.setImageResource(item.category.imageID)

    }
}