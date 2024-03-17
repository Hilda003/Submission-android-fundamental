package com.example.submission_android_fundamental.data.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_android_fundamental.data.retrofit.ApiConfig
import com.example.submission_android_fundamental.data.response.ResponseUser
import com.example.submission_android_fundamental.data.response.UserResult
import retrofit2.Call
import retrofit2.Callback


class MainViewModel : ViewModel() {

    val result = MutableLiveData<List<UserResult>>()
    val loading = MutableLiveData(View.GONE)
    val error = MutableLiveData<String>()


    fun searchResults(key: String){
        loading.postValue(View.VISIBLE)
        val client = ApiConfig.getApiService().getUser(key)
        client.enqueue(object : Callback<ResponseUser>{
            override fun onResponse(
                call: Call<ResponseUser>,
                response: retrofit2.Response<ResponseUser>
            ) {

                if (response.isSuccessful){
                    val data = response.body()
                    loading.postValue(View.GONE)
                    if (data?.totalCount == 0){
                        result.postValue(emptyList())
                        error.postValue("User $key not found")
                    } else {
                        result.postValue(data?.items)
                        loading.postValue(View.GONE)
                    }
                } else {
                    Log.e(TAG, "User not found: ${response.message()}")
                    error.postValue(response.message())
                }

            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {

                Log.e(TAG, "onFailure ${t.message}")
                error.postValue(t.message)
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}