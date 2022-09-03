package com.example.budgetbuddy.screens.transactions_screen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.databinding.TransactionsCustomRowBinding
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.room.transactions_table.TransactionsTable
import com.example.budgetbuddy.screens.search_screen.SearchTransactionFragmentDirections
import com.example.budgetbuddy.utils.getIncomeID


class TransactionFragmentAdapterChild(
    private val children: List<TransactionsTable>,
    private val searchFeature: Boolean
) :
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

        holder.binding.txtCategory.text = item.category?.rowValue
        if (item.catAmount!! > 0.00) {
            //Reporting Period
            if (item.category!!.uniqueID == getIncomeID()) {
                holder.binding.txtCostPrice.setTextColor(Color.parseColor("#ff00ddff"))
            }
            holder.binding.txtCostPrice.text = item.labels!!.catAmountLabel
            holder.binding.txtNote.text = ""
        } else {
            //Detailed
            if (item.category!!.uniqueID == getIncomeID()) {
                holder.binding.txtCostPrice.setTextColor(Color.parseColor("#ff00ddff"))
            }
            holder.binding.txtCostPrice.text = item.labels!!.amountLabel
            holder.itemView.setOnClickListener {
                val action = if (searchFeature) {
                    SearchTransactionFragmentDirections.actionNewSearchFragmentToTransactionDetailsFragment2(
                        item
                    )
                } else {
                    TransactionFragmentDirections.actionTransactionFragmentToTransactionDetailsFragment(
                        item
                    )
                }

                it.findNavController().navigate(action)
            }
            holder.binding.txtNote.text = item.note
        }

        holder.binding.imageView.setImageResource(CategoryList.getImageID(item.category!!.uniqueID)!!)
        //holder.binding.imageView.setImageResource(item.category.imageID)
    }
}