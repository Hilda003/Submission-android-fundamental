package com.example.submission_android_fundamental.data.response

import com.google.gson.annotations.SerializedName

data class ResponseDetailUser(


	@field: SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String,


)
