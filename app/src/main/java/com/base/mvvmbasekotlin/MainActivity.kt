package com.base.mvvmbasekotlin

import com.base.mvvmbasekotlin.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {


    override val layoutResId: Int
        get() = R.layout.activity_main
    override val layoutId: Int
        get() = R.id.container

    override fun initListener() {
    }

    override fun initView() {

    }

    override fun initData() {
    }
}
