package com.example.submission_android_fundamental.data.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission_android_fundamental.ui.FollowFragment
import com.example.submission_android_fundamental.utils.Constanta

class UserViewPagerAdapter(activity: AppCompatActivity, private val user:String ): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = FollowFragment.newInstance(
        user,
        when (position) {
            0 -> Constanta.FOLLOWERS
            else -> Constanta.FOLLOWING
        }
    )
}