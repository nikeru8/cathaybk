package com.daniel.cathaybk.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.cathaybk.dialog.UserDialog
import com.daniel.cathaybk.databinding.LoadingItemBinding
import com.daniel.cathaybk.databinding.UserItemBinding
import com.daniel.cathaybk.model.UserItem
import com.squareup.picasso.Picasso

class UserAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var TYPE_NORMAL = 0
    private var TYPE_FOOTER = 1

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

            binding.mainView.setOnClickListener {

                val dialog = UserDialog(activity = activity, model)
                dialog.show()

            }

        }

    }

    inner class FooterViewHolder(binding: LoadingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (TYPE_NORMAL == viewType) {

            val itemView = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserAdapterViewHolder(itemView)

        } else {

            val itemView = LoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FooterViewHolder(itemView)

        }

    }

    override fun getItemCount(): Int {

        return (totalUser.count()?.plus(1)) ?: 0

    }

    override fun getItemViewType(position: Int): Int {

        return if (position == itemCount - 1) {

            TYPE_FOOTER

        } else

            TYPE_NORMAL

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {

            TYPE_NORMAL -> {

                val item = totalUser.get(position)
                item?.let {

                    (holder as UserAdapterViewHolder).bind(it)

                }

            }

            else -> {//TYPE_FOOTER

                (holder as FooterViewHolder).bind()

            }


        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUser(data: MutableList<UserItem>) {

        totalUser.addAll(data)
        notifyDataSetChanged()

    }

    fun getUserSize(): String = totalUser.count().toString()

}


