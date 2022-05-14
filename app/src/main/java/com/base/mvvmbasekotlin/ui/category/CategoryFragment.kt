package com.base.mvvmbasekotlin.ui.category

import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment: BaseFragment() {
    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_category

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return false
    }

}