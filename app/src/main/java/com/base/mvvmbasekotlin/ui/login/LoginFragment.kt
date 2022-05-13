package com.base.mvvmbasekotlin.ui.login

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.dashboard.DashboardFragment
import com.base.mvvmbasekotlin.ui.register.RegisterFragment
import com.base.mvvmbasekotlin.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private lateinit var encodedString : String
    override fun backFromAddFragment() {
        val phoneRegister = arguments?.getString("phoneRegister", "")
        val passRegister = arguments?.getString("passRegister", "")

        if(phoneRegister != "" && passRegister != ""){
            edtPhone.setText(phoneRegister)
            edtPass.setText(passRegister)
        }
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
                if(checkBoxRememberLogin.isChecked){
                    viewModel.saveLoginSession(encodedString)
                } else {
                    viewModel.removeLoginSession()
                }
                getVC().addFragment(DashboardFragment::class.java)
            } else {
                txtError.text = Define.ToastMessage.SIGNIN_INVALID
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initData() {

        if(viewModel.checkLoginSession()){
            val jwt = viewModel.getLoginSession()
            if(jwt != null){
                edtPhone.setText(jwt[0])
                edtPass.setText(jwt[1])
                checkBoxRememberLogin.isChecked = true
            } else {
                checkBoxRememberLogin.isChecked = false
            }
        }
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
                encodedString = "Basic " + Base64Utils.encode("$phone:$password")
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