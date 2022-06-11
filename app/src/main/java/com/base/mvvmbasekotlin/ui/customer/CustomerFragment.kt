package com.base.mvvmbasekotlin.ui.customer

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.CustomerAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.customer.detail.CustomerDetailFragment
import com.base.mvvmbasekotlin.utils.MMKVHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_customer.*

@AndroidEntryPoint
class CustomerFragment: BaseFragment(context) {

    private var adapter = CustomerAdapter(contextFragment)

    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_customer

    override fun initView() {
        viewModel.getLoadingStatus().observe(requireActivity()) {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.hide()
            }
        }
        viewModel.getData().observe(requireActivity()) {
            if (it != null) {
                customerList.adapter = adapter
                adapter.addModels(it.data, true)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun initData() {
        val encodedString = MMKVHelper.getInstance().decodeString("jwt", "")
        viewModel.getAllCustomer(encodedString!!)
    }

    override fun initListener() {
        adapter.onClickItem = {
            val bundle = Bundle()
            bundle.putString("id", it.id)
            bundle.putString("fullName", it.fullName)
            bundle.putString("avatarUrl", it.avatarUrl)
            bundle.putString("phone", it.phone)
            bundle.putString("email", it.email)
            bundle.putInt("gender", it.gender)
            bundle.putString("dateOfBirth", it.dateOfBirth)
            getVC().addFragment(CustomerDetailFragment::class.java, bundle)
        }
    }

    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel : CustomerViewModel by viewModels()
}