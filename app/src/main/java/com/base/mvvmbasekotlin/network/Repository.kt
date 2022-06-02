package com.base.mvvmbasekotlin.network

import com.base.mvvmbasekotlin.models.request.StaffRegisterRequest
import com.base.mvvmbasekotlin.models.response.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface) {

    fun adminLogin(encodedString: String): Single<AuthResponse> {
        return apiInterface.adminLogin(encodedString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun staffRegister(encodedString: String, body: StaffRegisterRequest): Single<AuthResponse> {
        return apiInterface.staffRegister(encodedString, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllCategories(): Single<CategoryPreviewResponse> {
        return apiInterface.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addCategory(encodedString: String, displayName: String, cover: MultipartBody.Part): Single<CategoryResponse>{
        return apiInterface.addCateGory(encodedString, displayName, cover)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateCategory(encodedString: String, categoryID: Long, displayName: String, cover: MultipartBody.Part): Single<UpdateOkResponse>{
        return apiInterface.updateCateGory(encodedString,categoryID, displayName, cover)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteCategory(encodedString: String, categoryID: Long): Single<OkResponse>{
        return apiInterface.deleteCategory(encodedString,categoryID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}