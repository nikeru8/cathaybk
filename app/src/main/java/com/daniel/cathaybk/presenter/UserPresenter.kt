package com.daniel.cathaybk.presenter

import com.daniel.cathaybk.api.GitHubApi
import com.daniel.cathaybk.api.RetrofitManager
import com.daniel.cathaybk.model.User
import com.daniel.cathaybk.model.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPresenter(
    private val view: UserContract.View,
    private val retrofit: GitHubApi = RetrofitManager.callHomeFragmentService("首頁"),
) : UserContract.Presenter {

    var since: Int = 0

    override fun fetchUsers() {

        retrofit.getUsers(since, 20).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful) {

                    val model: MutableList<UserItem>? = response.body()

                    if (since == 0)
                        view.showUsers(response.body()!!)
                    else
                        view.updateData(response.body()!!)

                    try {

                        since = model!!.last().id!!

                    } catch (exception: Exception) {

                        view.showError(response.errorBody()?.string() ?: "since is null error - ${exception}")

                    }

                } else {

                    view.showError(response.errorBody()?.string() ?: "Unknown error response.isUnsuccessful")

                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {

                view.showError(t.message ?: "Network error")

            }

        })

    }

}