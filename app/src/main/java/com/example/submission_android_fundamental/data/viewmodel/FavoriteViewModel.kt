package com.example.submission_android_fundamental.data.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_android_fundamental.db.UserFavorite
import com.example.submission_android_fundamental.repo.FavoriteUserRepository

class FavoriteViewModel (application: Application) : ViewModel() {


    private val _userFavoriteRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllUserFavorite() : LiveData<List<UserFavorite>> = _userFavoriteRepository.getAllFavorites()

    fun unsetFavorite(username:String) {
        _userFavoriteRepository.unsetFavorite(username)
    }
}