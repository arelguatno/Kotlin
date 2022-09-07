package com.example.budgetbuddy.screens.wallets

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.TransactionsCustomRowBinding
import com.example.budgetbuddy.databinding.TransactionsParentBinding
import com.example.budgetbuddy.room.model.TransactionList
import com.example.budgetbuddy.room.wallet_table.Wallets
import com.example.budgetbuddy.utils.intDayToString
import com.example.budgetbuddy.utils.intMonthLongToString
import com.example.budgetbuddy.utils.transformSingleDigitToTwoDigit
import java.util.*

class WalletFragmentAdapter :
    ListAdapter<Wallets, WalletFragmentAdapter.MyViewHolder>(WORDS_COMPARATOR) {

    private lateinit var mListener: onItemClickListener
    private var defaultWalletId: Int = 0

    interface onItemClickListener {
        fun onItemClick(wallet: Wallets)
        fun onItemLongClicked(wallet: Wallets)
    }

    fun setItemOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    fun setDefaultID(v: Int){
        defaultWalletId = v
    }

    class MyViewHolder(val binding: TransactionsCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Wallets>() {
            override fun areItemsTheSame(
                oldItem: Wallets,
                newItem: Wallets
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: Wallets,
                newItem: Wallets
            ): Boolean {
                return oldItem.name == newItem.name
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.imageView.setImageResource(R.drawable.wallet)
        holder.binding.txtCategory.text = item.name
        holder.binding.txtCostPrice.isVisible = false
        holder.binding.txtNote.text = item.totalBalanceLabel

        if(defaultWalletId == item.id) {
            holder.binding.txtCostPrice.text = "Selected"
            holder.binding.txtCostPrice.isVisible = true
            holder.binding.txtCostPrice.setTextColor(Color.parseColor("#7BCEFF"))

        }

        val height = holder.binding.imageView.layoutParams.height
        val width = holder.binding.imageView.layoutParams.width

//        holder.itemView.setOnTouchListener { _, motionEvent ->
//
//            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
//                holder.binding.imageView.layoutParams.height = (height + 5)
//                holder.binding.imageView.layoutParams.width = (width + 5)
//                holder.binding.txtCategory.setTypeface(null, Typeface.BOLD)
//            }
//            if (motionEvent.action == MotionEvent.ACTION_UP || motionEvent.action == MotionEvent.ACTION_CANCEL) {
//                holder.binding.imageView.layoutParams.height = height
//                holder.binding.imageView.layoutParams.width = width
//                holder.binding.txtCategory.setTypeface(null, Typeface.NORMAL)
//            }
//            true
//        }

        holder.itemView.setOnClickListener {
            mListener.onItemClick(item)
        }

        holder.itemView.setOnLongClickListener {
            mListener.onItemLongClicked(item)
            true
        }
    }
}


