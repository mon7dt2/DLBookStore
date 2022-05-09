package com.base.mvvmbasekotlin.ui.register

import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.login.LoginFragment
import com.base.mvvmbasekotlin.utils.EditTextUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.edtPhone
import kotlinx.android.synthetic.main.login_fragment.imgClearPhone
import kotlinx.android.synthetic.main.register_fragment.*

@AndroidEntryPoint
class RegisterFragment: BaseFragment() {
    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.register_fragment

    override fun initView() {
        EditTextUtils.setEditText(edtName, imgClearName)
        EditTextUtils.setEditText(edtPhone, imgClearPhone)
    }

    override fun initData() {

    }

    override fun initListener() {
        btnLogin2.setOnClickListener {
            getVC().addFragment(LoginFragment::class.java)
        }
    }

    override fun backPressed(): Boolean {
        if(!getVC().backFromAddFragment()) requireActivity().finishAffinity()
        return false
    }

    private val viewModel: RegisterViewModel by viewModels()
}