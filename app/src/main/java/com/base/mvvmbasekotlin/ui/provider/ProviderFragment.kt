package com.base.mvvmbasekotlin.ui.provider

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.ProviderAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.provider.detail.ProviderDetailFragment
import com.base.mvvmbasekotlin.utils.MMKVHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_provider.*

@AndroidEntryPoint
class ProviderFragment: BaseFragment(context) {
    private var adapter = ProviderAdapter(contextFragment)

    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_provider

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
                providerList.adapter = adapter
                adapter.addModels(it.data.results,true)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun initData() {
        viewModel.getAllProvider(MMKVHelper.getInstance().decodeString("jwt", "")!!)
    }

    override fun initListener() {
        adapter.onClickItem = {
            val bundle = Bundle()
            bundle.putInt("providerId", it.id)
            bundle.putString("providerDisplayName", it.displayName)
            bundle.putString("providerDescription", it.description)
            bundle.putString("providerAddress", it.address)
            bundle.putString("providerPhone", it.phone)
            bundle.putString("providerEmail", it.email)
            getVC().addFragment(ProviderDetailFragment::class.java, bundle)
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
                getVC().addFragment(ProviderDetailFragment::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel: ProviderViewModel by viewModels()
}