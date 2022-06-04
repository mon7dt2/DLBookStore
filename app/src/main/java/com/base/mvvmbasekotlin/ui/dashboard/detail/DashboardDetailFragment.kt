package com.base.mvvmbasekotlin.ui.dashboard.detail

import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardDetailFragment: BaseFragment(context) {
    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_dashboard_detail

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel: DashboardViewModel by viewModels()
}