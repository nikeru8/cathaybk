package com.daniel.cathaybk

import com.daniel.cathaybk.model.UserItem

interface UserContract {

    interface View {
        fun showUsers(users: List<UserItem>)
        fun showError(message: String)
    }

    interface Presenter {
        fun fetchUsers()
    }

}