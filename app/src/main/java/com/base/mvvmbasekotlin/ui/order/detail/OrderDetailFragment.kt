package com.base.mvvmbasekotlin.ui.order.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.OrderDetailAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.models.data.Order
import com.base.mvvmbasekotlin.utils.MMKVHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order_detail.*
import java.util.*

@AndroidEntryPoint
class OrderDetailFragment: BaseFragment(context) {

    private val jwt = MMKVHelper.getInstance().decodeString("jwt")
    private var order:Order? = null
    private val adapter = OrderDetailAdapter(contextFragment)
    private val viewModel: OrderDetailViewModel by viewModels()

    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_order_detail

    override fun initView() {

    }

    override fun initData() {
        order = arguments?.getSerializable("order") as Order
        viewModel.getOrderDetail(jwt!!, order?.id!!)
        if(order?.orderStatus == 1 || order?.orderStatus == -1){
            btnConfirmOrder.isEnabled = false
            btnCancelOrder.isEnabled = false
        }
    }

    override fun initListener() {
        viewModel.getData().observe(viewLifecycleOwner, {
            if(it != null){
                orderDetailList.adapter = adapter
                adapter.clear()
                adapter.notifyDataSetChanged()
                adapter.addModels(it.data, true)
                adapter.notifyDataSetChanged()

                val r = Random()
                val token: Int = r.nextInt()
                Glide.with(requireContext())
                    .load(order?.customer?.avatarUrl + "?" + token)
                    .override(150, 150)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .placeholder(R.drawable.loading_icon)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imgCustomerDetail)
                txtODCName.text = order?.customer?.fullName
                txtODCPhone.text = order?.customer?.phone
                txtODCAddress.text = order?.address
            }
        })
        viewModel.getResponse().observe(viewLifecycleOwner, {
            if(it != null){
                val bundle = Bundle()
                bundle.putString("lastestFragment", "Order")
                getVC().backFromAddFragment(bundle)
            }
        })
        btnConfirmOrder.setOnClickListener {
            viewModel.updateOrderDetail(jwt!!, order?.id!!, 1)
        }
        btnCancelOrder.setOnClickListener {
            viewModel.updateOrderDetail(jwt!!, order?.id!!, -1)
        }
    }

    override fun backPressed(): Boolean {
        val bundle = Bundle()
        bundle.putString("lastestFragment", "Order")
        getVC().backFromAddFragment(bundle)
        return false
    }
}