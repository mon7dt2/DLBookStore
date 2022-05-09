package com.base.mvvmbasekotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tencent.mmkv.MMKV

abstract class BaseActivity : AppCompatActivity() {

    private var viewController : ViewController? = null

    fun getViewController() : ViewController {
        if(viewController == null){
            viewController = ViewController(layoutId,supportFragmentManager)

        }
        return viewController!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        viewController = ViewController(layoutId,supportFragmentManager)
        initView()
        initData()
        initListener()
        MMKV.initialize(this);
    }

    override fun onBackPressed() {
        if(viewController!= null && viewController?.currentFragment!=null){
            if(viewController?.currentFragment?.backPressed() == true){
                super.onBackPressed()
            }
        }else{
            super.onBackPressed()
        }

    }

    protected abstract val layoutResId :  Int
    protected abstract val layoutId :  Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()

}