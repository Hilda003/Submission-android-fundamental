package com.example.submission_android_fundamental.ui

import android.content.DialogInterface
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.submission_android_fundamental.R
import com.example.submission_android_fundamental.data.adapter.UserViewPagerAdapter
import com.example.submission_android_fundamental.databinding.ActivityDetailBinding
import com.example.submission_android_fundamental.data.viewmodel.DetailViewModel
import com.example.submission_android_fundamental.data.viewmodel.UserFactory
import com.example.submission_android_fundamental.data.viewmodel.UserViewModel
import com.example.submission_android_fundamental.db.UserFavorite
import com.example.submission_android_fundamental.utils.Constanta
import com.google.android.material.tabs.TabLayoutMediator
import com.example.submission_android_fundamental.utils.Help
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.NullPointerException
import java.lang.StringBuilder

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding ? = null

    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var detailViewModel: DetailViewModel
    private val mContext = this
    private val help = Help



    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        detailViewModel = obtainView(mContext)

        detailViewModel.isLoading.observe(this) {
            help.loading(it, binding.progressBar)
        }

        username = intent.getStringExtra(Constanta.USERNAME).toString()

        TabLayoutMediator(
            binding.tabsLayout,
            binding.ViewPager.apply {
                this.adapter = UserViewPagerAdapter(mContext, username)
            }) { tab, position -> tab.text = resources.getString(Constanta.TAB[position]) }.attach()



        detailViewModel.apply {

            this.setUserData(username)
            this.isCheckUserFavorite(username).observe(mContext) {
                if (it != null) {
                    this.isUserFavorite.postValue(true)
                } else {
                    this.isUserFavorite.postValue(false)
                }
            }
            this.userData.observe(mContext) {
                binding.apply {

                    tvUsername.text = StringBuilder("@").append(it.login)
                    tvName.text = defaultValue(it.name, "name not set")
                    tvTwitter.text = defaultValue(it.reposUrl, "Twitter not set")
                    tvTotalFollowers.text = it.followers.toString()
                    tvTotalFollowing.text = it.following.toString()
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .into(imageUser)

                    val username = it.login
                    val name = it.name
                    val avatarUrl = it.avatarUrl

                    val dataFavorite = UserFavorite(
                        username ?: "",
                        name ?: "",
                        avatarUrl ?: ""
                    )
                    detailViewModel.initFavorite(dataFavorite)


                }
            }

            this.error.observe(
                mContext
            ) {
                Help.toast(mContext, it)

            }
        }

        detailViewModel.apply {
            this.error.observe(
                mContext
            ) {
                Help.toast(mContext, it)
            }
        }

        detailViewModel.isUserFavorite.observe(mContext) {
            if (it == true) {
                binding.favoriteButton.setImageResource(
                    R.drawable.outline_favorite
                )

            } else {
                binding.favoriteButton.setImageResource(
                    R.drawable.favorite
                )
            }
        }

        binding.favoriteButton.setOnClickListener {
            if (detailViewModel.isUserFavorite.value == true) {
                MaterialAlertDialogBuilder(mContext)
                    .setTitle("Delete Favorite")
                    .setMessage(StringBuilder().append("$username is already favorite"))
                    .setCancelable(true)
                    .setIcon(R.drawable.delete)
                    .setPositiveButton("Delete Favorite?") { _, _ ->
                        detailViewModel.unsetFavorite(username)
                        Help.toast(mContext, "$username is deleted")



                    }
                    .setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
                    .show()
            } else {
                detailViewModel.apply {
                    this.userFavorites.value?.let {
                        this.setFavorite(it)
                        Help.toast(mContext, "Successfully add  $username to favorite")

                    }
                }
            }


        }






    }

    private fun defaultValue(value: String, defaultValue: String = "-"): String {
        try {
            if (value.isNotEmpty()) {
                return value
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return defaultValue

    }

    private fun obtainView(activity: AppCompatActivity): DetailViewModel {
        val userFactory = UserFactory.getInstance(activity.application)
        return ViewModelProvider(activity, userFactory)[DetailViewModel::class.java]
    }


}

