package com.example.submission_android_fundamental.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 5)
abstract class UserFavoriteRoom : RoomDatabase(){
    abstract fun userFavoriteDao(): UserFavoriteDAO

    companion object{
        @Volatile
        private var instance: UserFavoriteRoom? = null


        @JvmStatic
        fun getDb(context: Context) : UserFavoriteRoom {
            if (instance == null){
                synchronized(UserFavoriteRoom::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserFavoriteRoom::class.java, "user-favorite"
                    )

                        .build()
                }
            }
            return instance as UserFavoriteRoom
        }
    }
}