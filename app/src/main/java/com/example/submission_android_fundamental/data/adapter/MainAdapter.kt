package com.example.submission_android_fundamental.data.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_android_fundamental.ui.DetailActivity
import com.example.submission_android_fundamental.data.response.UserResult
import com.example.submission_android_fundamental.databinding.UserRowItemBinding

import com.example.submission_android_fundamental.utils.Constanta

class MainAdapter( private val context: Context) : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {

    private var data = mutableListOf<UserResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataUser = data[position]
        holder.apply {
            tvUsername.text = dataUser.login
            Glide.with(context)
                .load(dataUser.avatarUrl)
                .into(tvImage)
            itemView.setOnClickListener{
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Constanta.USERNAME, dataUser.login)
                context.startActivity(intent)
            }
        }

    }
    override fun getItemCount(): Int {
        return data.size
    }
    fun clearData() {
        data.clear()
    }
    fun initData(userList: List<UserResult>) {
        data.clear()
        data = userList.toMutableList()
    }
    class ListViewHolder(private val binding: UserRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvUsername = binding.tvUsername
        val tvImage = binding.imageUser
    }
}
