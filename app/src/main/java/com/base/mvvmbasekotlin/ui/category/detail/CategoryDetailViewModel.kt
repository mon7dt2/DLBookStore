package com.base.mvvmbasekotlin.ui.category.detail

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.CategoryResponse
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(val repo: Repository): BaseViewModel(){
    private val response = MutableLiveData<String>()
    private val data = MutableLiveData<CategoryResponse?>()

    var coverPath: String?=null

    fun getResponse() : MutableLiveData<String> {
        return response
    }
    fun getData(): MutableLiveData<CategoryResponse?> {
        return data
    }

    fun addCategory(encodedString: String, displayName: String, cover: MultipartBody.Part){
        mDisposable.add(repo.addCategory(encodedString, displayName, cover)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                response.postValue(it.msg)
                data.postValue(it)
            },{
                response.postValue(it.message)
                data.postValue(null)
            }))
    }

    fun updateCategory(encodedString: String, categoryID: Long, displayName: String, cover: MultipartBody.Part?){
        mDisposable.add(repo.updateCategory(encodedString, categoryID, displayName, cover)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                response.postValue(it.msg)
            },{
                response.postValue(it.message)
            }))
    }
}