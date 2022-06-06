package com.base.mvvmbasekotlin.ui.order

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.OrderAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.order.detail.OrderDetailFragment
import com.base.mvvmbasekotlin.utils.MMKVHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order.*

@AndroidEntryPoint
class OrderFragment: BaseFragment(context) {

    private val viewModel: OrderViewModel by viewModels()
    private val adapter= OrderAdapter(contextFragment)
    private val jwt = MMKVHelper.getInstance().decodeString("jwt")

    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_order

    override fun initView() {
        setHasOptionsMenu(true)
    }

    override fun initData() {

    }

    override fun onResume() {
        viewModel.getAllOrders(jwt!!)
        super.onResume()
    }

    override fun initListener() {
        viewModel.getResponse().observe(viewLifecycleOwner, {
            if(it != null){
                orderList.adapter = adapter
                adapter.clear()
                adapter.notifyDataSetChanged()
                orderList.addItemDecoration(
                    DividerItemDecoration(
                        context,
                        LinearLayoutManager.VERTICAL
                    )
                )
                adapter.addModels(it.data,true)
                adapter.notifyDataSetChanged()
            }
        })
        adapter.onClickItem= {
            val bundle = Bundle()
            bundle.putSerializable("order", it)
            getVC().addFragment(OrderDetailFragment::class.java, bundle)
        }
    }

    override fun backPressed(): Boolean {
       return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater1 = requireActivity().menuInflater
        inflater1.inflate(R.menu.menu_order, menu)
        super.onCreateOptionsMenu(menu, inflater1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_state_0 -> {
                viewModel.getOrderByState(jwt!!, 0)
            }
            R.id.item_state_1 -> {
                viewModel.getOrderByState(jwt!!, 1)
            }
            R.id.item_state_n1 -> {
                viewModel.getOrderByState(jwt!!, -1)
            }
            R.id.item_state_all -> {
                viewModel.getAllOrders(jwt!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}