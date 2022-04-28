package com.base.mvvmbasekotlin.ui.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.utils.ValidatePhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

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
        btnLogin.setOnClickListener {
            var phone = edtPhone.text.toString().trim();
            if (ValidatePhone().validate(phone)){
                Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Not OK", Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun backPressed(): Boolean {
        return false;
    }

    private val viewModel: LoginViewModel by viewModels();
}