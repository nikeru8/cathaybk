package com.daniel.cathaybk.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.cathaybk.databinding.UserItemBinding
import com.daniel.cathaybk.model.UserItem
import com.squareup.picasso.Picasso

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserAdapterViewHolder>() {

    var totalUser = mutableListOf<UserItem>()

    inner class UserAdapterViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("CheckResult", "SetTextI18n")
        fun bind(model: UserItem) {

            Picasso.get().load(model.avatarUrl ?: "").into(binding.userImage)
            binding.username.text = model.login

            with(binding.userStatus) {

                if (model.siteAdmin == true) {

                    text = "STAFF"
                    visibility = View.VISIBLE

                } else {

                    text = ""
                    visibility = View.INVISIBLE

                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapterViewHolder {

        return UserAdapterViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: UserAdapterViewHolder, position: Int) {

        holder.bind(totalUser[position])

    }

    override fun getItemCount(): Int {

        return totalUser.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUser(data: MutableList<UserItem>) {

        Log.d("TAG", "chckpoint ${data[1].login}")
        data.forEach { item ->

            totalUser.add(item)

        }

        notifyDataSetChanged()

    }

}


