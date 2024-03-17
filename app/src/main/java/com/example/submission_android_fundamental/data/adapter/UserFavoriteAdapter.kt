package com.example.submission_android_fundamental.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_android_fundamental.databinding.UserRowItemBinding
import com.example.submission_android_fundamental.db.UserFavorite
import com.example.submission_android_fundamental.ui.DetailActivity
import com.example.submission_android_fundamental.utils.Constanta
import java.util.ArrayList
import androidx.recyclerview.widget.DiffUtil

class UserFavoriteAdapter: RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>() {

    private val userFavorite = ArrayList<UserFavorite>()


    class ViewHolder(private val binding: UserRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserFavorite){
            with(binding) {
                tvUsername.text = user.username
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .into(imageUser)
            }
            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(Constanta.USERNAME, user.username)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userRowItemBinding = UserRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userRowItemBinding)
    }

    override fun getItemCount(): Int {
       return userFavorite.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userFavorites = userFavorite[position]
        holder.bind(userFavorites)
    }

    fun getUserFavorite(userFav: List<UserFavorite>) {
        val diffCallback = DiffCallbackUserFavorite(this.userFavorite, userFav)
        val result = DiffUtil.calculateDiff(diffCallback)
        this.userFavorite.clear()
        this.userFavorite.addAll(userFav)
        result.dispatchUpdatesTo(this)
    }


    class DiffCallbackUserFavorite(private val oldUser: List<UserFavorite>, private val newUser: List<UserFavorite>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldUser.size
        }

        override fun getNewListSize(): Int {
            return newUser.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldUser[oldItemPosition].username == newUser[newItemPosition].username

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldUser[oldItemPosition]
            val newItem = newUser[newItemPosition]
            return oldItem.username == newItem.username
        }

    }

    fun getSwipedUsername(position: Int): String {
        return userFavorite[position].username
    }


}