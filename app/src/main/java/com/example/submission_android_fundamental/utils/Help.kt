package com.example.submission_android_fundamental.utils

import android.content.Context
import android.view.View
import android.widget.Toast

object Help {

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun loading(loading: Boolean, view: View) {
        view.visibility = if (loading) View.VISIBLE else View.GONE
    }

//    fun getData() : String {
//        val
//    }
}