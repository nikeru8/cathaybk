package com.daniel.cathaybk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.cathaybk.adapter.UserAdapter
import com.daniel.cathaybk.databinding.ActivityMainBinding
import com.daniel.cathaybk.model.UserItem

class MainActivity : AppCompatActivity(), UserContract.View {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var presenter: UserContract.Presenter
    lateinit var binding: ActivityMainBinding

    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()

    }

    private fun initData() {

        //init adapter
        userAdapter = UserAdapter()
        binding.recyclerView.adapter = userAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // init Presenter
        presenter = UserPresenter(this)
        // call api
        presenter.fetchUsers()

    }

    override fun showUsers(users: MutableList<UserItem>) {

        Log.d(TAG, "call api showUsers success")
        userAdapter.updateUser(users)

    }

    override fun showError(message: String) {

        Log.d(TAG, "call api showError failure")

    }


}