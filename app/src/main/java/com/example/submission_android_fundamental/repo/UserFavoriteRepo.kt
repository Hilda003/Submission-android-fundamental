package com.example.submission_android_fundamental.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission_android_fundamental.db.UserFavorite
import com.example.submission_android_fundamental.db.UserFavoriteDAO
import com.example.submission_android_fundamental.db.UserFavoriteRoom
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: UserFavoriteDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserFavoriteRoom.getDb(application)
        mFavoriteUserDao = db.userFavoriteDao()
    }

    fun getAllFavorites(): LiveData<List<UserFavorite>> = mFavoriteUserDao.getAllUserFavorite()

    fun insertUserFavorite(user: UserFavorite) {
        executorService.execute { mFavoriteUserDao.setUserFavorite(user) }
    }

    fun unsetFavorite(username:String) {
        executorService.execute { mFavoriteUserDao.unsetUserFavorite(username) }
    }


    fun checkUpdateFavorite(user: String) : LiveData<UserFavorite> {
        return mFavoriteUserDao.isFavoriteExist(user)
    }
}