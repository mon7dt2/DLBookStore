package com.base.mvvmbasekotlin.ui.login

import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.login_fragment;

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return false;
    }

    private val viewModel: LoginViewModel by viewModels();
}