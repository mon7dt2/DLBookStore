package com.base.mvvmbasekotlin.ui.product

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.ProductAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.category.detail.CategoryDetailFragment
import com.base.mvvmbasekotlin.ui.product.detail.ProductDetailFragment
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
        viewModel.getLoadingStatus().observe(requireActivity()) {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.hide()
            }
        }
        viewModel.getResponse().observe(requireActivity()) {
            if (it != null) {
                productList.adapter = adapter
                adapter.addModels(it.data.results, true)
                adapter.notifyDataSetChanged()
            }
        }
        adapter.onClickItem = {
            val bundle = Bundle()
            bundle.putSerializable("product", it)
            bundle.putString("stateGo", "update")
            getVC().addFragment(ProductDetailFragment::class.java, bundle)
        }
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