package com.base.mvvmbasekotlin.ui.category

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.adapter.CategoryAdapter
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.ui.category.detail.CategoryDetailFragment
import com.base.mvvmbasekotlin.utils.Define
import com.base.mvvmbasekotlin.utils.MMKVHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_dashboard.*


@AndroidEntryPoint
class CategoryFragment: BaseFragment(context) {
    private var adapter = CategoryAdapter(contextFragment)

    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.fragment_category

    override fun onResume() {
        adapter.clear()
        adapter.notifyDataSetChanged()
        viewModel.getAllCategories()
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
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
            if (it == "HTTP OK") {
                categoryList.adapter = adapter
            }
        }
        viewModel.getData().observe(requireActivity()) {
            if (it != null) {
                categoryList.adapter = adapter
                adapter.addModels(it.results, true)
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getDeleteStatus().observe(viewLifecycleOwner) {
            if (it?.code == 200) {
                adapter.clear()
                adapter.notifyDataSetChanged()
                viewModel.getAllCategories()
            }
        }
    }

    override fun initData() {
        //viewModel.getAllCategories()
    }

    override fun initListener() {
        adapter.onClickCategory = {
            val bundle = Bundle()
            bundle.putString("categoryName", it.displayName)
            bundle.putInt("categoryId", it.id.toInt())
            bundle.putString("categoryUrl", it.avatarUrl)
            getVC().addFragment(CategoryDetailFragment::class.java, bundle)
        }
        adapter.onClickRemoveCategory = {
            showDialog(it.id)
        }
    }

    override fun backPressed(): Boolean {

        return false
    }

    private fun showDialog(id: Long) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.deleteCategory))
        builder.setMessage(getString(R.string.confirmDeleteCate))
        builder.setPositiveButton(getString(R.string.accept)) { _, _ ->
            viewModel.deleteCategory(MMKVHelper.getInstance().decodeString("jwt", "")!!, id)
        }
        builder.setNegativeButton(getString(R.string.reject)) { _, _ ->
        }
        builder.show()
    }

    private val viewModel: CategoryViewModel by viewModels()
}