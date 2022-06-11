package com.base.mvvmbasekotlin.ui.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.base.ViewController
import com.base.mvvmbasekotlin.ui.category.CategoryFragment
import com.base.mvvmbasekotlin.ui.category.detail.CategoryDetailFragment
import com.base.mvvmbasekotlin.ui.customer.CustomerFragment
import com.base.mvvmbasekotlin.ui.dashboard.detail.DashboardDetailFragment
import com.base.mvvmbasekotlin.ui.order.OrderFragment
import com.base.mvvmbasekotlin.ui.product.ProductFragment
import com.base.mvvmbasekotlin.ui.product.detail.ProductDetailFragment
import com.base.mvvmbasekotlin.ui.provider.ProviderFragment
import com.base.mvvmbasekotlin.ui.provider.detail.ProviderDetailFragment
import com.base.mvvmbasekotlin.utils.MMKVHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*

@AndroidEntryPoint
class DashboardFragment: BaseFragment(context){

    private var currentState = "Home"

    override fun backFromAddFragment() {
        currentState = arguments?.getString("lastestFragment", "").toString()
        if(currentState != "") {
            when(currentState){
                "Category" -> {
                    replaceFragment(CategoryFragment(), R.id.containerDashboard, animation = false)
                }
                "Provider" -> {
                    replaceFragment(ProviderFragment(), R.id.containerDashboard, animation = false)
                }
                "Customer" -> {
                    replaceFragment(CustomerFragment(), R.id.containerDashboard, animation = false)
                }
                "Product" -> {
                    replaceFragment(ProductFragment(), R.id.containerDashboard, animation = false)
                }
                "Order" -> {
                    replaceFragment(OrderFragment(), R.id.containerDashboard, animation = false)
                }
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_dashboard

    override fun initView() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        setHasOptionsMenu(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.title = "Trang chủ"
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            requireActivity(),
            drawer_layout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){}
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)

        drawerToggle.syncState()
        nav_view.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId){
                R.id.nav_category -> {
                    currentState = "Category"
                    toolbar.menu.clear()
                    actionbar?.title = "Danh mục"
                    toolbar.inflateMenu(R.menu.menu_category)
                    replaceFragment(CategoryFragment(), R.id.containerDashboard, animation = false)
                }
                R.id.nav_home -> {
                    actionbar?.title = "Trang chủ"
                    currentState = "Home"
                    toolbar.menu.clear()
                    replaceFragment(DashboardDetailFragment(), R.id.containerDashboard, animation = false)
                }
                R.id.nav_customer -> {
                    actionbar?.title = "Khách hàng"
                    currentState = "Customer"
                    toolbar.menu.clear()
                    replaceFragment(CustomerFragment(), R.id.containerDashboard, animation = false)
                }
                R.id.nav_provider -> {
                    actionbar?.title = "Nhà cung cấp"
                    toolbar.menu.clear()
                    currentState = "Provider"
                    toolbar.inflateMenu(R.menu.menu_add_provider)
                    replaceFragment(ProviderFragment(), R.id.containerDashboard, animation = false)
                }
                R.id.nav_product -> {
                    actionbar?.title = "Sản phẩm"
                    toolbar.menu.clear()
                    currentState = "Product"
                    toolbar.inflateMenu(R.menu.menu_add_product)
                    replaceFragment(ProductFragment(), R.id.containerDashboard, animation = false)
                }
                R.id.nav_order -> {
                    actionbar?.title = "Hoá đơn"
                    toolbar.menu.clear()
                    currentState = "Order"
                    toolbar.inflateMenu(R.menu.menu_order)
                    replaceFragment(OrderFragment(), R.id.containerDashboard, animation = false)
                }
            }
            drawer_layout.closeDrawers()
            true
        }
        replaceFragment(DashboardDetailFragment(), R.id.containerDashboard, animation = false)

        val hView = nav_view.getHeaderView(0)
        val txtHeaderName = hView.findViewById<TextView>(R.id.txtHeaderName)
        val imgHeader = hView.findViewById<ImageView>(R.id.imgHeader)
        txtHeaderName.text = MMKVHelper.getInstance().decodeString("fullName", "")
        Glide.with(requireContext())
                .load(MMKVHelper.getInstance().decodeString("avatarUrl", ""))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_staff)
                .into(imgHeader)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
            R.id.itemAddCategory -> {
                getVC().addFragment(CategoryDetailFragment::class.java)
            }
            R.id.itemAddProduct -> {
                val bundle = Bundle()
                bundle.putString("stateGo", "add")
                getVC().addFragment(ProductDetailFragment::class.java, bundle)
            }
            R.id.itemAddProvider -> {
                getVC().addFragment(ProviderDetailFragment::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return true
    }

    private val viewModel: DashboardViewModel by viewModels()
}