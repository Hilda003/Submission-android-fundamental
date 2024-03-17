package com.example.submission_android_fundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_android_fundamental.data.adapter.UserFavoriteAdapter
import com.example.submission_android_fundamental.data.viewmodel.FavoriteViewModel
import com.example.submission_android_fundamental.data.viewmodel.UserFactory
import com.example.submission_android_fundamental.databinding.ActivityUserFavoriteBinding
import com.example.submission_android_fundamental.utils.Help

class UserFavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityUserFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel : FavoriteViewModel
    private lateinit var favoriteAdapter : UserFavoriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }



        favoriteAdapter = UserFavoriteAdapter()
        binding.rvUser.apply {
            val llm = LinearLayoutManager(this@UserFavoriteActivity)
            llm.orientation = LinearLayoutManager.VERTICAL
            binding.rvUser.layoutManager = llm
            binding.rvUser.adapter = favoriteAdapter
        }

        favoriteViewModel = obtainViewModel(this@UserFavoriteActivity)
        favoriteViewModel.getAllUserFavorite().observe(this) {
            if (it != null) {
                favoriteAdapter.getUserFavorite(it)
                binding.emptyView.visibility = View.GONE
            } else {
                binding.emptyView.visibility = View.VISIBLE

            }
            favoriteAdapter.notifyDataSetChanged()
        }

        val swipe = object : SwipeCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val username = favoriteAdapter.getSwipedUsername(position)
                binding.rvUser.adapter?.let {
                    it.notifyItemRemoved(position)
                    it.notifyDataSetChanged()
                    favoriteViewModel.unsetFavorite(username)
                    Help.toast(this@UserFavoriteActivity, "$username dihapus dari favorite")
                }
            }

        }
        ItemTouchHelper(swipe).attachToRecyclerView(binding.rvUser)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = UserFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}