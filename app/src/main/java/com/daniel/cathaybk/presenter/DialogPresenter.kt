package com.daniel.cathaybk.presenter

import com.daniel.cathaybk.api.RetrofitManager
import com.daniel.cathaybk.model.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogPresenter(private val view: UserContract.DialogView) : UserContract.DialogPresenter {

    private val retrofit = RetrofitManager.callHomeFragmentService("首頁")

    override fun fetchUserDetail(userID: String) {

        retrofit.getUserDetail(userID).enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {

                if (response.isSuccessful) {

                    val model: UserDetail = response.body()!!
                    view.showUsers(model)

                } else {

                    view.showError(response.errorBody()?.string() ?: "Unknown error")

                }

            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {

                view.showError(t.message ?: "Unknown error")

            }
        })
    }
}