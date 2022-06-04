package com.base.mvvmbasekotlin.ui.category.detail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.base.mvvmbasekotlin.BaseApplication.Companion.context
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.BaseFragment
import com.base.mvvmbasekotlin.utils.Define
import com.base.mvvmbasekotlin.utils.FileUtil
import com.base.mvvmbasekotlin.utils.MMKVHelper
import com.base.mvvmbasekotlin.utils.RealPathUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category_detail.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


@AndroidEntryPoint
class CategoryDetailFragment: BaseFragment(context) {
    val REQUEST_CODE_IMAGE_STORAGE = 12111
    var isUpdateCover = false
    var isHaveBaseCover = false
    var filePart: MultipartBody.Part? = null

    override fun backFromAddFragment() {
    }

    override val layoutId: Int
        get() = R.layout.fragment_category_detail

    override fun initView() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbarCategory)
        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        setMenuVisibility(false)
        if(arguments?.getInt("categoryId", 0) == 0){
            actionbar?.title = getString(R.string.addCategory)
            imgCategoryDetail.setImageResource(R.drawable.add_image)
            edtCateName.setText("")
        } else {
            actionbar?.title = getString(R.string.categoryDetail)
            isHaveBaseCover = true
            val r = Random()
            val token = r.nextInt()
            Glide.with(contextFragment)
                .load(arguments?.getString("categoryUrl", "")+ "?" + token)
                .override(180, 180)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgCategoryDetail)
            edtCateName.setText(arguments?.getString("categoryName", ""))
        }
        requireActivity().invalidateOptionsMenu()
    }

    override fun initData(){}

    override fun initListener() {
        imgCategoryDetail.setOnClickListener{
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

        btnSendCategoryData.setOnClickListener{
            if(!isHaveBaseCover){
                if( !isUpdateCover || edtCateName.text == null){
                    Toast.makeText(requireContext(),Define.ToastMessage.INVALID_INPUT, Toast.LENGTH_SHORT).show()
                } else {
                    addCategory()
                }

            } else {
                if(edtCateName.text == null){
                    Toast.makeText(requireContext(),Define.ToastMessage.INVALID_INPUT, Toast.LENGTH_SHORT).show()
                } else if (isUpdateCover){
                    editCategory() // with image
                } else {
                    updateCategory() //within image
                }
            }
        }

        viewModel.getResponse().observe(viewLifecycleOwner, {
            if(it == "Ok"){
                Toast.makeText(requireContext(),Define.ToastMessage.INPUT_SUCCESS, Toast.LENGTH_SHORT).show()
                getVC().backFromAddFragment()
            } else if(it == "HTTP 409"){
                Toast.makeText(requireContext(),Define.ToastMessage.CATEGORY_EXIST, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(),it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun addCategory(){
        filePart = MultipartBody.Part.createFormData(
            "cover",
            "uploadFile",
            RequestBody.create(Define.Api.IMAGE_TYPE.toMediaTypeOrNull(), File(viewModel.coverPath!!))
        )

        viewModel.addCategory(
            MMKVHelper.getInstance().decodeString("jwt")!!,
            edtCateName.text.toString().trim(),
            filePart!!
        )
    }

    fun updateCategory(){
        viewModel.updateCategory(
            MMKVHelper.getInstance().decodeString("jwt")!!,
            arguments?.getInt("categoryId",0)!!.toLong(),
            edtCateName.text.toString().trim(),
            null
        )
    }

    fun editCategory(){
        filePart = MultipartBody.Part.createFormData(
            "cover",
            "uploadFile",
            RequestBody.create(Define.Api.IMAGE_TYPE.toMediaTypeOrNull(), File(viewModel.coverPath!!))
        )
        viewModel.updateCategory(
            MMKVHelper.getInstance().decodeString("jwt")!!,
            arguments?.getInt("categoryId", 0)!!.toLong(),
            edtCateName.text.toString().trim(),
            filePart!!
        )
    }

    private fun startGallery() {
        val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        cameraIntent.type = Define.Api.IMAGE_TYPE
        startActivityForResult(cameraIntent, REQUEST_CODE_IMAGE_STORAGE)
    }

    override fun backPressed(): Boolean {
        val bundle = Bundle()
        bundle.putString("lastestFragment", "Category")
        getVC().backFromAddFragment(bundle)
        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.itemAddCategory)
        item.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{
                Toast.makeText(requireContext(),Define.ToastMessage.INPUT_SUCCESS, Toast.LENGTH_SHORT).show()
                getVC().backFromAddFragment()
            }
        }
        return super.onOptionsItemSelected(item)
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
                            .into(imgCategoryDetail)
                    }
                }
            }
        }
    }

    private val viewModel: CategoryDetailViewModel by viewModels()
}