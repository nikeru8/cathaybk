package com.daniel.cathaybk.api

import com.daniel.cathaybk.model.User
import com.daniel.cathaybk.model.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    /**
     * https://api.github.com/
     * */
    @GET("users")
    fun getUsers(@Query("since") since: Int, @Query("per_page") perPage: Int): Call<User>

    /**
     * https://api.github.com/
     * */
    @GET("users/{id}")
    fun getUserDetail(@Path("id") id: String): Call<UserDetail>

}