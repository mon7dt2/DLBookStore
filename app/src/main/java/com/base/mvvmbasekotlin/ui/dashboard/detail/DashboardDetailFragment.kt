package com.base.mvvmbasekotlin.ui.dashboard.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.TotalCategoryAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.models.data.ItemData
import com.base.mvvmbasekotlin.ui.dashboard.DashboardViewModel
import com.base.mvvmbasekotlin.utils.MMKVHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard_detail.*

@AndroidEntryPoint
class DashboardDetailFragment: BaseFragment(context) {
    private var total: Int = 0;
    private val adapter = TotalCategoryAdapter(contextFragment)
    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.fragment_dashboard_detail

    override fun initView() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        actionbar?.isHideOnContentScrollEnabled = false
    }

    override fun initData() {
        viewModel.getTotalCategory(MMKVHelper.getInstance().decodeString("jwt")!!)
    }

    override fun initListener() {
        viewModel.getData().observe(viewLifecycleOwner, { res ->
            val array: List<ItemData>
            if(res != null){
                array = res.data.entries.map {
                    total += it.value.toInt()
                    ItemData(it.key, it.value.toInt())
                }
                sumCateItem.text = total.toString()
                listviewCate.adapter = adapter
                adapter.clear()
                adapter.notifyDataSetChanged()
                adapter.addModels(array, true)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel: DashboardDetailViewModel by viewModels()
}