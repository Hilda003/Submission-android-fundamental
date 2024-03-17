package com.example.submission_android_fundamental.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance : UserFactory? = null

        @JvmStatic
        fun getInstance(application: Application) : UserFactory {
            if (instance == null) {
                synchronized(UserFactory::class.java) {
                    instance = UserFactory(application)
                }
            }
            return instance as UserFactory
        }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}