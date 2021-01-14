package com.sample.kotlinboilerplate.ui.main.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.kotlinboilerplate.R
import com.sample.kotlinboilerplate.data.local.entity.User
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(
    private val users: ArrayList<User>,
    private val userClick: onUserClick
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.textViewUserName.text = user.name
            itemView.textViewUserEmail.text = user.email
            Glide.with(itemView.imageViewAvatar.context)
                .load(user.avatar).placeholder(R.drawable.no_image)
                .into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
        val userId = bundleOf("user_id" to users[position].id)
        holder.itemView.setOnClickListener {
            userClick.userClicked(userId)
            /*Navigation.findNavController(holder.itemView).navigate(
                R.id.user_details,
                userId)*/
        }
    }


    fun addData(list: List<User>) {
        users.addAll(list)
    }
    interface onUserClick{
        fun userClicked(userId: Bundle);
    }
}
