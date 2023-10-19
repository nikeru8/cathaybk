package com.daniel.cathaybk.presenter

import com.daniel.cathaybk.model.UserDetail
import com.daniel.cathaybk.model.UserItem

interface UserContract {

    interface View {

        fun showUsers(users: MutableList<UserItem>)
        fun updateData(users: MutableList<UserItem>)
        fun showError(message: String)

    }

    interface Presenter {

        fun fetchUsers()

    }

    interface DialogView {

        fun showUsers(user: UserDetail)
        fun showError(message: String)

    }


    interface DialogPresenter {

        fun fetchUserDetail(userID: String)

    }

}