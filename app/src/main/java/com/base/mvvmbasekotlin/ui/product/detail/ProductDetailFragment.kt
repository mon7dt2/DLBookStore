package com.base.mvvmbasekotlin.ui.product.detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.extension.hideKeyboard
import com.base.mvvmbasekotlin.models.data.CategoryPreview
import com.base.mvvmbasekotlin.models.data.ProductPreview
import com.base.mvvmbasekotlin.utils.Define
import com.base.mvvmbasekotlin.utils.FileUtil
import com.base.mvvmbasekotlin.utils.MMKVHelper
import com.base.mvvmbasekotlin.utils.RealPathUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category_detail.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

@AndroidEntryPoint
class ProductDetailFragment: BaseFragment(context) {

    private val viewModel: ProductDetailViewModel by viewModels()
    val REQUEST_CODE_IMAGE_STORAGE = 12111
    var currentCategorySelected: Long? = null
    var currentProviderSelected: Int? = null
    var isUpdateCover = false
    var filePart: MultipartBody.Part? = null
    val jwt = MMKVHelper.getInstance().decodeString("jwt")
    val listCate = mutableListOf<CharSequence>()
    val listCateID = mutableListOf<Long>()
    val listProvider = mutableListOf<CharSequence>()
    val listProviderID = mutableListOf<Int>()

    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_product_detail

    override fun initView() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbarProduct)
        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        actionbar?.isHideOnContentScrollEnabled = false
        actionbar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun initData() {
        if(arguments?.getString("stateGo").equals("update")){
            val product = arguments?.getSerializable("product") as ProductPreview
            val r = Random()
            val token = r.nextInt()
            Glide.with(contextFragment)
                .load(product.avatarUrl + "?" + token)
                .override(180, 180)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgProductCover)
            edtProductName.setText(product.displayName)
            edtProductAuthor.setText(product.author)
            edtProductPrice.setText(product.price.toString())
            edtProductPublisher.setText(product.publisher)
            edtProductQuantity.setText(product.quantity.toString())
            edtProductDescription.setText(product.description)
            edtProductProvider.setText(product.category.displayName)
            edtProductCate.setText(product.provider.displayName)
            currentCategorySelected = product.category.id.toLong()
            currentProviderSelected = product.provider.id
        }
        viewModel.getAllCategories()
        viewModel.getAllProvider(jwt!!)
    }

    override fun initListener() {
        viewModel.getDataCategory().observe(viewLifecycleOwner) {
            if (it?.results?.isNotEmpty() == true) {
                for (category in it.results) {
                    listCate.add(category.displayName)
                    listCateID.add(category.id)
                    if (arguments?.getString("stateGo").equals("update")) {
                        val product = arguments?.getSerializable("product") as ProductPreview
                        if (category.id == product.category.id.toLong()) {
                            edtProductCate.setText(category.displayName)
                        }
                    }
                }
            }
        }
        viewModel.getDataProvider().observe(viewLifecycleOwner) {
            if (it?.results?.isNotEmpty() == true) {
                for (provider in it.results) {
                    listProvider.add(provider.displayName)
                    listProviderID.add(provider.id)
                    if (arguments?.getString("stateGo").equals("update")) {
                        val product = arguments?.getSerializable("product") as ProductPreview
                        if (provider.id == product.provider.id) {
                            edtProductProvider.setText(provider.displayName)
                        }
                    }
                }
            }
        }
        edtProductCate.setOnClickListener {
            showDialogCategory()
        }
        edtProductProvider.setOnClickListener {
            showDialogProvider()
        }
        btnSendProductData.setOnClickListener {
            edtProductDescription.hideKeyboard()
            if(edtProductName.text != null && edtProductDescription.text != null && edtProductAuthor.text != null &&
                    edtProductPrice.text != null && edtProductPublisher.text != null && currentCategorySelected != null &&
                    currentProviderSelected != null && edtProductQuantity.text != null){
                if(arguments?.getString("stateGo").equals("update")){
                    //update
                    val id = (arguments?.getSerializable("product") as ProductPreview).id
                    if(isUpdateCover){
                        filePart = MultipartBody.Part.createFormData(
                            "avatar",
                            "uploadFile",
                            RequestBody.create(Define.Api.IMAGE_TYPE.toMediaTypeOrNull(), File(viewModel.coverPath!!))
                        )
                        viewModel.updateProduct(jwt!!, id, edtProductName.text.toString(), edtProductDescription.text.toString(),
                            edtProductPrice.text.toString().toFloat(), edtProductQuantity.text.toString().toInt(),
                            edtProductAuthor.text.toString(), edtProductPublisher.text.toString(),currentCategorySelected!!,
                            currentProviderSelected!!.toLong(),filePart)
                    } else {
                        viewModel.updateProduct(jwt!!, id, edtProductName.text.toString(), edtProductDescription.text.toString(),
                            edtProductPrice.text.toString().toFloat(), edtProductQuantity.text.toString().toInt(),
                            edtProductAuthor.text.toString(), edtProductPublisher.text.toString(),currentCategorySelected!!,
                            currentProviderSelected!!.toLong(),null)
                    }
                } else {
                    //add
                    if(isUpdateCover){
                        filePart = MultipartBody.Part.createFormData(
                            "avatar",
                            "uploadFile",
                            RequestBody.create(Define.Api.IMAGE_TYPE.toMediaTypeOrNull(), File(viewModel.coverPath!!))
                        )
                        viewModel.addProduct(jwt!!, edtProductName.text.toString(), edtProductDescription.text.toString(),
                                    edtProductPrice.text.toString().toFloat(), edtProductQuantity.text.toString().toInt(),
                                    edtProductAuthor.text.toString(), edtProductPublisher.text.toString(),currentCategorySelected!!,
                                    currentProviderSelected!!.toLong(),filePart!!)
                    }else {
                        Toast.makeText(requireContext(),Define.ToastMessage.INVALID_INPUT, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
             Toast.makeText(requireContext(),Define.ToastMessage.INVALID_INPUT, Toast.LENGTH_SHORT).show()
            }
        }
        imgProductCover.setOnClickListener{
            //open gallery
            if(ActivityCompat.checkSelfPermission(contextFragment,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_IMAGE_STORAGE)
            }
            else {
                startGallery()
            }
        }
        viewModel.getDataResponse().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), Define.ToastMessage.INPUT_SUCCESS, Toast.LENGTH_SHORT)
                .show()
            val bundle = Bundle()
            bundle.putString("lastestFragment", "Product")
            getVC().backFromAddFragment(bundle)
        }
    }

    override fun backPressed(): Boolean {
        val bundle = Bundle()
        bundle.putString("lastestFragment","Product")
        getVC().backFromAddFragment(bundle)
        return false
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_product, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.itemDelete -> {
                showDialogDelete((arguments?.getSerializable("product") as ProductPreview).id)
            }
            android.R.id.home -> {
                val bundle = Bundle()
                bundle.putString("lastestFragment","Product")
                getVC().backFromAddFragment(bundle)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startGallery() {
        val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        cameraIntent.type = Define.Api.IMAGE_TYPE
        startActivityForResult(cameraIntent, REQUEST_CODE_IMAGE_STORAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_IMAGE_STORAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val listUri = data?.data ?: return
                    viewModel.coverPath = RealPathUtil.getRealPath(requireContext(), listUri)
                    isUpdateCover = true
                    FileUtil.getFileFromUri(context, listUri)?.path?.let {
                        Glide.with(contextFragment)
                            .load(it)
                            .override(180, 180)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .placeholder(R.drawable.loading_icon)
                            .error(R.drawable.ic_launcher_foreground)
                            .into(imgProductCover)
                    }
                }
            }
        }
    }

    private fun showDialogCategory() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.selectCategory))
        val charArray = listCate.toTypedArray()
        builder.setSingleChoiceItems(charArray, -1) {dialogInterface, i ->
            edtProductCate.setText(charArray[i])
            currentCategorySelected = listCateID[i]
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(getString(R.string.reject)) { _, _ ->
        }
        builder.show()
    }

    private fun showDialogProvider(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.selectProvider))
        val charArray = listProvider.toTypedArray()
        builder.setSingleChoiceItems(charArray, -1) {dialogInterface, i ->
            edtProductProvider.setText(charArray[i])
            currentProviderSelected = listProviderID[i]
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(getString(R.string.reject)) { _, _ ->
        }
        builder.show()
    }

    private fun showDialogDelete(id: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.deleteProduct))
        builder.setMessage(getString(R.string.confirmDeleteProduct))
        builder.setPositiveButton(getString(R.string.accept)) { _, _ ->
            viewModel.deleteProduct(jwt!!, id)
        }
        builder.setNegativeButton(getString(R.string.reject)) { _, _ ->
        }
        builder.show()
    }
}