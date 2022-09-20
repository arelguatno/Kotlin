package com.example.roomapp.fragments.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row.view.*

class AdapterUserPaging: PagingDataAdapter<User, AdapterUserPaging.UserItem>(AdapterUserPaging.DIFF_CALLBACK) {

    inner class UserItem(v: View): RecyclerView.ViewHolder(v)

    companion object{
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: UserItem, position: Int) {
        val currentItem = getItem(position)

        Log.d("arelguatno", "item: $currentItem")

        holder.itemView.id_txt.text = (position + 1).toString()
        holder.itemView.firstName_txt.text = currentItem?.firstName
        holder.itemView.lastName_txt.text = currentItem?.lastName
        holder.itemView.age_txt.text = currentItem?.age.toString()

        holder.itemView.rowLayout.setOnClickListener {
            // Set navigation fragment to accept user model as an argument
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem!!) // currentItem = UserModel
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItem {
      return UserItem(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }
}