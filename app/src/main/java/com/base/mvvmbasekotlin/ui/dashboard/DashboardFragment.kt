package com.base.mvvmbasekotlin.ui.dashboard

import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.base.ViewController
import com.base.mvvmbasekotlin.ui.category.CategoryFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*

@AndroidEntryPoint
class DashboardFragment: BaseFragment(context){
    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.fragment_dashboard

    override fun initView() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_menu)
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
                    replaceFragment(CategoryFragment(), R.id.containerDashboard, animation = false)
                }
            }
            drawer_layout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return false
    }

    private val viewModel: DashboardViewModel by viewModels()
}