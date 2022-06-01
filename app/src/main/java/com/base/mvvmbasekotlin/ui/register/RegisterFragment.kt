package com.base.mvvmbasekotlin.ui.register

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.extension.hideKeyboard
import com.base.mvvmbasekotlin.models.request.StaffRegisterRequest
import com.base.mvvmbasekotlin.ui.login.LoginFragment
import com.base.mvvmbasekotlin.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.imgClearPhone
import kotlinx.android.synthetic.main.register_fragment.*

@AndroidEntryPoint
class RegisterFragment: BaseFragment(context) {
    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.register_fragment

    override fun initView() {
        EditTextUtils.setEditText(edtName, imgClearName)
        EditTextUtils.setEditText(edtPhone2, imgClearPhone)
        EditTextUtils.setEditText(edtMail, imgClearMail)

        viewModel.getResponse().observe(requireActivity(), {
            if(it.equals("HTTP OK")){
                Toast.makeText(requireContext(), Define.ToastMessage.REGISTER_STAFF_SUCCESS, Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("phoneRegister", edtPhone2.text.toString().trim())
                bundle.putString("passRegister", edtPass2.text.toString().trim())
                getVC().backFromAddFragment(bundle)
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun initData() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initListener() {
        btnLogin2.setOnClickListener {
            getVC().addFragment(LoginFragment::class.java)
        }
        btnRegister2.setOnClickListener {
            if(edtName.text.toString().trim() == "" || edtPhone2.text.toString().trim() == "" ||
                edtPass2.text.toString().trim() == "" || edtRePass.text.toString().trim() == ""){
                    edtName.setText("")
                    edtPhone2.setText("")
                    edtPass2.setText("")
                    edtRePass.setText("")
                    Toast.makeText(requireContext(), R.string.invalidInput, Toast.LENGTH_SHORT).show()
            } else if(edtPass2.text.toString().trim() != edtRePass.text.toString().trim()){
                Toast.makeText(requireContext(), R.string.passNotMatch, Toast.LENGTH_SHORT).show()
            } else if(!ValidatePhone.validate(edtPhone2.text.toString().trim())){
                Toast.makeText(requireContext(), R.string.invalidPhone, Toast.LENGTH_SHORT).show()
            } else if(!ValidatePassword.validate(edtPass2.text.toString().trim())) {
                Toast.makeText(requireContext(), R.string.invalidPass, Toast.LENGTH_SHORT).show()
            } else if(!ValidateEmail.validate(edtMail.text.toString().trim())){
                Toast.makeText(requireContext(), R.string.invalidEmail, Toast.LENGTH_SHORT).show()
            } else {
                val body = StaffRegisterRequest(edtName.text.toString().trim(),edtPhone2.text.toString().trim(),edtPhone2.text.toString().trim(),
                   1,edtMail.text.toString().trim(),edtPhone2.text.toString().trim())
                val encodedString = "Basic " + Base64Utils.encode(edtPhone2.text.toString().trim() +":" +edtPass2.text.toString().trim())
                viewModel.registerNewStaff(encodedString, body)
            }
        }
    }

    override fun backPressed(): Boolean {
        requireActivity().currentFocus?.hideKeyboard()
        //if(!getVC().backFromAddFragment()) requireActivity().finishAffinity()
        return false
    }

    private val viewModel: RegisterViewModel by viewModels()
}