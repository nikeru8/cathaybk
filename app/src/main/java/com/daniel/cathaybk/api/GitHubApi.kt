package com.daniel.cathaybk.api

import com.daniel.cathaybk.model.User
import retrofit2.Call
import retrofit2.http.GET

interface GitHubApi {

    /**
     * https://api.github.com/
     * */
    @GET("users")
    fun getUsers(): Call<User>

}