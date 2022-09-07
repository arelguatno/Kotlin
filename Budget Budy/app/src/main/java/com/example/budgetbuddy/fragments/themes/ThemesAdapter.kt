package com.example.budgetbuddy.fragments.themes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.databinding.TransactionsCustomRowBinding
import com.example.budgetbuddy.fragments.category.SimpleListObject


class ThemesAdapter(private val list: List<Pair<Int, SimpleListObject>>, val themesID: Int) :
    RecyclerView.Adapter<ThemesAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(currencyObj: SimpleListObject)
    }

    fun setItemOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class MyViewHolder(val binding: TransactionsCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
        val item = list[position]

        holder.binding.imageView.setImageResource(item.second.imageID)
        holder.binding.txtNote.text = item.second.textIcon
        holder.binding.txtCategory.text = item.second.rowValue
        holder.binding.txtCostPrice.isVisible = false

        if (themesID == item.first) {
            holder.binding.txtCostPrice.setTextColor(Color.WHITE)
            holder.binding.txtCostPrice.text = "Selected"
            holder.binding.txtCostPrice.setTextColor(Color.parseColor("#7BCEFF"))
            holder.binding.txtCostPrice.isVisible = true
        }

        holder.itemView.setOnClickListener {
            mListener.onItemClick(item.second)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

