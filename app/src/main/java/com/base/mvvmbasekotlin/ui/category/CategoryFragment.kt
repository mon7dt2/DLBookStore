package com.base.mvvmbasekotlin.ui.category

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.CategoryAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.category.detail.CategoryDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category.*


@AndroidEntryPoint
class CategoryFragment: BaseFragment(context) {
    private var adapter = CategoryAdapter(contextFragment)

    override fun backFromAddFragment() {
        viewModel.getAllCategories()
    }

    override val layoutId: Int
        get() = R.layout.fragment_category

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
        adapter.onClickCategory = {
            val bundle = Bundle()
            bundle.putString("categoryName", it.displayName)
            bundle.putInt("categoryId", it.id.toInt())
            bundle.putString("categoryUrl", it.coverUrl)
            getVC().addFragment(CategoryDetailFragment::class.java, bundle)
        }
        adapter.onClickRemoveCategory = {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater1 = requireActivity().menuInflater
        inflater1.inflate(R.menu.menu_category, menu)
        super.onCreateOptionsMenu(menu, inflater1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.itemAddCategory -> {
                getVC().addFragment(CategoryDetailFragment::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel: CategoryViewModel by viewModels()
}