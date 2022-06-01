package com.base.mvvmbasekotlin.ui.category

import android.content.Context
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.CategoryAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category.*

@AndroidEntryPoint
class CategoryFragment: BaseFragment(context) {
    private var adapter = CategoryAdapter(contextFragment)


    override fun backFromAddFragment() {}

    override val layoutId: Int
        get() = R.layout.fragment_category

    override fun initView() {
        viewModel.getLoadingStatus().observe(requireActivity(), {
            if (it){
                loadingDialog.show()
            } else {
                loadingDialog.hide()
            }
        })
        viewModel.getResponse().observe(requireActivity(), {
            if(it == "HTTP OK"){
                categoryList.adapter = adapter
            }
        })
        viewModel.getData().observe(requireActivity(), {
            if(it != null){
                categoryList.adapter = adapter
                adapter.addModels(it.results,true)
                adapter.notifyDataSetChanged()

            }
        })
    }

    override fun initData() {
        viewModel.getAllCategories()
    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel: CategoryViewModel by viewModels()
}