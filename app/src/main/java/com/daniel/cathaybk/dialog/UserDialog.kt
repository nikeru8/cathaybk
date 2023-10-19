package com.daniel.cathaybk.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View.*
import androidx.databinding.DataBindingUtil
import com.daniel.cathaybk.R
import com.daniel.cathaybk.presenter.UserContract
import com.daniel.cathaybk.databinding.DialogUserDetailBinding
import com.daniel.cathaybk.model.UserDetail
import com.daniel.cathaybk.model.UserItem
import com.daniel.cathaybk.presenter.DialogPresenter
import com.squareup.picasso.Picasso


@SuppressLint("CheckResult")
class UserDialog(var activity: Activity, var model: UserItem) :
    Dialog(activity, R.style.fullScreenDialogWithoutAnimation), UserContract.DialogView {

    private val TAG = UserDialog::class.java.simpleName
    var binding: DialogUserDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_user_detail, null, false)

    private var presenter: UserContract.DialogPresenter = DialogPresenter(this@UserDialog)

    init {

        setContentView(binding.root)

        initData()
        initView()
        initListener()

    }

    private fun initData() {

        presenter.fetchUserDetail(model.login ?: "")

    }

    private fun initView() {

        Picasso.get().load(model.avatarUrl ?: "").into(binding.userAvatar)
        with(binding.tvUserStatus) {

            if (model.siteAdmin == true) {

                text = "STAFF"
                visibility = VISIBLE

            } else {

                text = ""
                visibility = GONE

            }

        }

    }

    private fun initListener() {

        binding.closeIcon.setOnClickListener {

            dismiss()

        }

    }

    //call api success
    override fun showUsers(user: UserDetail) {

        with(binding) {

            tvUserName.text = user.name ?: ""
            if (user.bio?.isNotBlank() == true)
                Picasso.get().load(user.bio ?: "").into(ivBioIcon)
            tvLoginName.text = user.login ?: ""
            tvLocation.text = user.location ?: ""
            tvLink.text = user.blog ?: ""

        }

    }

    //call api fail
    override fun showError(message: String) {


    }

}
