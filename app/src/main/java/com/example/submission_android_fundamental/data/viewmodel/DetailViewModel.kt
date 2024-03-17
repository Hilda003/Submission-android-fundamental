package com.example.submission_android_fundamental.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_android_fundamental.data.retrofit.ApiConfig
import com.example.submission_android_fundamental.data.response.ResponseDetailUser
import com.example.submission_android_fundamental.db.UserFavorite
import com.example.submission_android_fundamental.repo.FavoriteUserRepository
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(mApplication: Application) : ViewModel() {
     val userData = MutableLiveData<ResponseDetailUser>()
    val error = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = loading
    val isUserFavorite = MutableLiveData<Boolean>()
     val userFavorites = MutableLiveData<UserFavorite>()
    private val userFavoriteRepo = FavoriteUserRepository(mApplication)

    fun isCheckUserFavorite(user: String) : LiveData<UserFavorite> =
        userFavoriteRepo.checkUpdateFavorite(user)

    fun setFavorite(favorite: UserFavorite) {
        userFavoriteRepo.insertUserFavorite(favorite)
    }

    fun unsetFavorite(username:String) {
        userFavoriteRepo.unsetFavorite(username)
    }
    fun initFavorite(favorite: UserFavorite) {
        userFavorites.postValue(favorite)
    }


    fun setUserData(username: String){
        loading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<ResponseDetailUser>{
            override fun onResponse(
                call: Call<ResponseDetailUser>,
                response: Response<ResponseDetailUser>
            ) {
                loading.value = false
                if (response.isSuccessful) {
                    userData.postValue(response.body())

                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                    error.postValue(response.message())
                }
            }
            override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
                loading.value = false
                Log.d(TAG, "onFailure: $t")

                error.postValue(t.message)
            }
        })
    }
    companion object {
        private val TAG = DetailViewModel::class.simpleName
    }

}