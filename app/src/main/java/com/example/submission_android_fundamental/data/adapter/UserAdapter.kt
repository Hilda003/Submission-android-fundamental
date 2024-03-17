package com.example.submission_android_fundamental.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_android_fundamental.data.response.UserResult
import com.example.submission_android_fundamental.databinding.UserRowItemBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var data = mutableListOf<UserResult>()
    class ViewHolder(private val binding: UserRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var tvUsername = binding.tvUsername
        var tvImageUrl = binding.imageUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataFollow = data[position]
        holder.apply {
            tvUsername.text = dataFollow.login
            Glide.with(itemView)
                .load(dataFollow.avatarUrl)
                .into(tvImageUrl)
        }
    }
    fun initData(dataFollow: List<UserResult>) {
        data.clear()
        data = dataFollow.toMutableList()
    }


    override fun getItemCount(): Int {
        return data.size
    }
}