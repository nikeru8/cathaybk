package com.daniel.cathaybk

import com.daniel.cathaybk.api.RetrofitManager
import com.daniel.cathaybk.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserPresenter(private val view: UserContract.View) : UserContract.Presenter {

    private val retrofit = RetrofitManager.callHomeFragmentService("首頁")

    override fun fetchUsers() {

        retrofit.getUsers().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful) {

                    view.showUsers(response.body()!!)

                } else {

                    view.showError(response.errorBody()?.string() ?: "Unknown error")

                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {

                view.showError(t.message ?: "Network error")

            }


        })

    }

}