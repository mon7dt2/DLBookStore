package com.base.mvvmbasekotlin.ui.customer.detail

import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_customer_detail.*
import java.util.*

@AndroidEntryPoint
class CustomerDetailFragment: BaseFragment(context) {
    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_customer_detail

    override fun initView() {
        val r = Random()
        val token: Int = r.nextInt()

        Glide.with(requireContext())
            .load(arguments?.getString("avatarUrl", "") + "?" + token)
            .override(120, 120)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .placeholder(R.drawable.loading_icon)
            .error(R.drawable.ic_launcher_foreground)
            .into(imgCustomer)

        edtCtmName.setText(arguments?.getString("fullName", ""))
        edtCtmPhone.setText(arguments?.getString("phone", ""))
        edtCtmEmail.setText(arguments?.getString("email", ""))
        edtCtmBirth.setText(arguments?.getString("dateOfBirth", ""))
        val gender = if (arguments?.getInt("gender", 0) == 0) "Nữ" else "Nam"
        edtCtmGender.setText(gender)
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        getVC().backFromAddFragment()
        return false
    }

    private val viewModel: CustomerDetailViewModel by viewModels()
}