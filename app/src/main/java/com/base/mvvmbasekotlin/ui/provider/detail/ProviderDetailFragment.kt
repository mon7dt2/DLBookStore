package com.base.mvvmbasekotlin.ui.provider.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.models.request.ProviderBodyRequest
import com.base.mvvmbasekotlin.utils.Define
import com.base.mvvmbasekotlin.utils.MMKVHelper
import com.base.mvvmbasekotlin.utils.ValidateEmail
import com.base.mvvmbasekotlin.utils.ValidatePhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_provider_detail.*

@AndroidEntryPoint
class ProviderDetailFragment: BaseFragment(context) {
    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_provider_detail

    override fun initView() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbarProvider)
        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        actionbar?.isHideOnContentScrollEnabled = false
        actionbar?.setDisplayHomeAsUpEnabled(true)
        this.setHasOptionsMenu(true)
    }

    override fun initData() {
        if(arguments?.getInt("providerId") != null){
            edtPrvName.setText(arguments?.getString("providerDisplayName", ""))
            edtPrvPhone.setText(arguments?.getString("providerPhone", ""))
            edtPrvEmail.setText(arguments?.getString("providerEmail", ""))
            edtPrvDescription.setText(arguments?.getString("providerDescription", ""))
            edtPrvAddress.setText(arguments?.getString("providerAddress", ""))
        } else {
            txtDeleteProvider.visibility = GONE
        }
    }

    override fun initListener() {
        btnSendProviderData.setOnClickListener {
            if(edtPrvName.text == null || edtPrvPhone.text == null || edtPrvEmail.text == null ||
                edtPrvDescription.text == null || edtPrvAddress.text == null){
                Toast.makeText(requireContext(),Define.ToastMessage.INVALID_INPUT, Toast.LENGTH_SHORT).show()
            } else if (!ValidatePhone.validate(edtPrvPhone.text.toString())){
                Toast.makeText(requireContext(),Define.ToastMessage.SIGNIN_PHONE_INVALID, Toast.LENGTH_SHORT).show()
            } else if (!ValidateEmail.validate(edtPrvEmail.text.toString())) {
                Toast.makeText(requireContext(),Define.ToastMessage.EMAIL_INVALID, Toast.LENGTH_SHORT).show()
            } else {
                val body = ProviderBodyRequest(edtPrvName.text.toString(), edtPrvDescription.text.toString(),
                    edtPrvPhone.text.toString(),edtPrvAddress.text.toString(),edtPrvEmail.text.toString())
                if(arguments?.getInt("providerId", 0) != 0 && arguments?.getInt("providerId", 0) != null ){
                    viewModel.updateProvider(MMKVHelper.getInstance().decodeString("jwt", "")!!,
                        arguments?.getInt("providerId", 0)!!,body)
                } else {
                    viewModel.addProvider(MMKVHelper.getInstance().decodeString("jwt", "")!!, body)
                }
            }
        }
        txtDeleteProvider.setOnClickListener {
            viewModel.deleteProvider(MMKVHelper.getInstance().decodeString("jwt", "")!!,
                arguments?.getInt("providerId", 0)!!)
        }
        viewModel.getAddResponse().observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    Define.ToastMessage.INPUT_SUCCESS,
                    Toast.LENGTH_SHORT
                ).show()
                val bundle = Bundle()
                bundle.putString("lastestFragment", "Provider")
                getVC().backFromAddFragment(bundle)
            } else {
                Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getOkResponse().observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    Define.ToastMessage.INPUT_SUCCESS,
                    Toast.LENGTH_SHORT
                ).show()
                val bundle = Bundle()
                bundle.putString("lastestFragment", "Provider")
                getVC().backFromAddFragment(bundle)
            } else {
                Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val bundle = Bundle()
                bundle.putString("lastestFragment", "Order")
                getVC().backFromAddFragment(bundle)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun backPressed(): Boolean {
        val bundle = Bundle()
        bundle.putString("lastestFragment", "Order")
        getVC().backFromAddFragment(bundle)
        return false
    }

    private val viewModel: ProviderDetailViewModel by viewModels()
}