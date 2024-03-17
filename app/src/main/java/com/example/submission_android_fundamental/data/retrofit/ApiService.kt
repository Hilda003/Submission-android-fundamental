package com.example.submission_android_fundamental.data.retrofit



import com.example.submission_android_fundamental.data.response.ResponseDetailUser
import com.example.submission_android_fundamental.data.response.ResponseUser
import com.example.submission_android_fundamental.data.response.UserResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") id : String
    ) : Call<ResponseUser>

    @GET("users/{login}/followers")
    fun getUserFollowers(
        @Path("login") id: String
    ): Call<List<UserResult>>

    @GET("users/{login}/following")
    fun getUserFollowing(
        @Path("login") id: String
    ): Call<List<UserResult>>


    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") id: String
    ): Call<ResponseDetailUser>
}

