package com.example.submission_android_fundamental.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserFavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setUserFavorite(user: UserFavorite)

    @Query("DELETE FROM UserFavorite WHERE UserFavorite.username = :username")
    fun unsetUserFavorite(username: String)

    @Query("DELETE FROM UserFavorite")
    fun deleteAllUserFavorite()

    @Query("SELECT * FROM UserFavorite ORDER BY username ASC")
    fun getAllUserFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT * FROM UserFavorite WHERE username = :username")
    fun isFavoriteExist(username: String): LiveData<UserFavorite>
}