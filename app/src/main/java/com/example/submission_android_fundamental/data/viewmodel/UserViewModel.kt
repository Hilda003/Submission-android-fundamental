package com.example.submission_android_fundamental.data.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.example.submission_android_fundamental.data.retrofit.ApiConfig
import com.example.submission_android_fundamental.data.response.UserResult
import com.example.submission_android_fundamental.utils.Constanta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    val userData = MutableLiveData<List<UserResult>>()
    val loading = MutableLiveData(View.VISIBLE)
    val error = MutableLiveData<String>()



    fun setUserData(follow: String, username: String) {
        val client = when(follow) {
            Constanta.FOLLOWERS -> ApiConfig.getApiService().getUserFollowers(username)
            else -> ApiConfig.getApiService().getUserFollowing(username)
        }
        client.enqueue(object : Callback<List<UserResult>>{
            override fun onResponse(
                call: Call<List<UserResult>>,
                response: Response<List<UserResult>>
            ) {
                if (response.isSuccessful) {
                    userData.postValue(response.body())
                    loading.postValue(View.GONE)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    error.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserResult>>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
                error.postValue(t.message)
            }
        })
    }
    companion object {
        private val TAG = UserViewModel::class.simpleName
    }

}