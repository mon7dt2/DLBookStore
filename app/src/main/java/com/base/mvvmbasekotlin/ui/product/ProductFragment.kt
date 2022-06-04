package com.base.mvvmbasekotlin.ui.product

import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.ProductAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product.*

@AndroidEntryPoint
class ProductFragment: BaseFragment(context) {

    private var adapter = ProductAdapter(contextFragment)

    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_product

    override fun onResume() {
        adapter.clear()
        adapter.notifyDataSetChanged()
        viewModel.getAllProducts()
        super.onResume()
    }

    override fun initView() {
        setHasOptionsMenu(true)
        viewModel.getLoadingStatus().observe(requireActivity(), {
            if (it){
                loadingDialog.show()
            } else {
                loadingDialog.hide()
            }
        })
        viewModel.getResponse().observe(requireActivity(), {
            if(it != null){
                productList.adapter = adapter
                adapter.addModels(it.data,true)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel: ProductViewModel by viewModels()
}