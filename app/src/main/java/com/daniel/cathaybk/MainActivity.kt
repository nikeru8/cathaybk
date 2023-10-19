package com.daniel.cathaybk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.cathaybk.adapter.UserAdapter
import com.daniel.cathaybk.databinding.ActivityMainBinding
import com.daniel.cathaybk.model.UserItem
import com.daniel.cathaybk.presenter.UserContract
import com.daniel.cathaybk.presenter.UserPresenter

class MainActivity : AppCompatActivity(), UserContract.View {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var presenter: UserContract.Presenter
    lateinit var binding: ActivityMainBinding

    lateinit var userAdapter: UserAdapter
    var totalDenominator = "0"

    //true 可以繼續往下讀取
    //false 阻擋
    private var isloading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()

    }

    private fun initData() {

        // init Presenter
        presenter = UserPresenter(this)
        // call api
        presenter.fetchUsers()

    }

    private fun initView() {

        binding.title.text = "Users"
        binding.recyclerView.let { recyclerview ->

            //init adapter
            userAdapter = UserAdapter(this@MainActivity)

            val llm = LinearLayoutManager(this)
            llm.orientation = LinearLayoutManager.VERTICAL
            recyclerview.layoutManager = llm

            recyclerview.adapter = userAdapter

            //分批顯示
            recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (totalDenominator != "0") binding.title.text = getString(R.string.user_title, lastVisibleItem.toString(), totalDenominator)
                    if (isloading && dy > 0 && lastVisibleItem == totalItemCount - 1) { //滑動到底部
                        isloading = false
                        presenter.fetchUsers()

                    }
                }
            })
        }

    }

    override fun showUsers(users: MutableList<UserItem>) {

        userAdapter.updateUser(users)
        totalDenominator = userAdapter.getUserSize()

    }

    override fun updateData(users: MutableList<UserItem>) {

        userAdapter.updateUser(users)
        totalDenominator = userAdapter.getUserSize()
        isloading = true

    }

    override fun showError(message: String) {

        Log.d(TAG, "call api showError failure")

    }

}