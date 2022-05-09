package com.base.mvvmbasekotlin.ui.login

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.register.RegisterFragment
import com.base.mvvmbasekotlin.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.login_fragment

    override fun initView() {
        EditTextUtils.setEditText(edtPhone, imgClearPhone)
        viewModel.loading.observe(this, { isLoading: Boolean? ->
            if (isLoading != null) {
                if (isLoading) {
                    loadingDialog.show()
                } else {
                    loadingDialog.hide()
                }
            }
        })
        viewModel.getResponse().observe(requireActivity(), { response : String? ->
            if(response.equals("HTTP OK")){
                Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
            } else {
                txtError.text = "Sai tên đăng nhập hoặc mật khẩu"
            }
        })
    }

    override fun initData() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initListener() {
        btnLogin1.setOnClickListener {
            val phone = edtPhone.text.toString().trim()
            val password = edtPass.text.toString().trim()
            txtError.text = ""
            if (!ValidatePassword.validate(password)){
                Toast.makeText(requireContext(), Define.ToastMessage.SIGNIN_PASSWORD_INVALID, Toast.LENGTH_SHORT).show()
                edtPhone.setText("")
                edtPass.setText("")
            } else if (!ValidatePhone.validate(phone)){
                Toast.makeText(requireContext(), Define.ToastMessage.SIGNIN_PHONE_INVALID, Toast.LENGTH_SHORT).show()
            } else {
                val encodedString = "Basic " + Base64Utils.encode("$phone:$password")
                viewModel.login(encodedString)
            }
        }
        btnRegister1.setOnClickListener{
            getVC().addFragment(RegisterFragment::class.java)
        }
    }

    override fun backPressed(): Boolean {
        getVC().removeAllFragment()
        return true
    }

    private val viewModel: LoginViewModel by viewModels()
}