package com.base.mvvmbasekotlin.ui.splash

import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.base.permission.PermissionHelper
import com.base.mvvmbasekotlin.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private val permissionHelper: PermissionHelper by lazy {
        PermissionHelper()
    }

    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.splash_fragment;

    override fun initView() {
        var i = 20
        Thread {
            while (i < 101) {
                i += 4
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                if (i >=100) getVC().replaceFragment(LoginFragment::class.java)
            }
        }.start()
    }

    override fun initData() {
        //TODO("Not yet implemented")
    }

    override fun initListener() {
        //TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel: SplashViewModel by viewModels()
}