package com.daniel.cathaybk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.daniel.cathaybk.model.UserItem

class MainActivity : AppCompatActivity(), UserContract.View {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var presenter: UserContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化 Presenter
        presenter = UserPresenter(this)

        // 在適當的位置呼叫 fetchUsers
        presenter.fetchUsers()

    }

    override fun showUsers(users: List<UserItem>) {

        Log.d(TAG, "call api showUsers success")

    }

    override fun showError(message: String) {

        Log.d(TAG, "call api showError failure")

    }


}