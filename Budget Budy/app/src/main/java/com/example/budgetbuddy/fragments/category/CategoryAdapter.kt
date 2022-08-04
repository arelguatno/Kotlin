package com.example.budgetbuddy.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.databinding.SimplelistCustomRowBinding
import com.example.budgetbuddy.fragments.category.SimpleListObject


class CategoryAdapter(private val list: List<Pair<Int, SimpleListObject>>) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(categoryObj: SimpleListObject)
    }

    fun setItemOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class MyViewHolder(val binding: SimplelistCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SimplelistCustomRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]

        holder.binding.textView2.text = item.second.rowValue
        holder.binding.imageView2.setImageResource(item.second.imageID)

        holder.itemView.setOnClickListener {
            mListener.onItemClick(item.second)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

