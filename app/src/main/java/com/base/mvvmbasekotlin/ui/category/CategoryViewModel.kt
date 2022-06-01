package com.base.mvvmbasekotlin.ui.category

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.result.ResultCategoryPreview
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(val repo: Repository): BaseViewModel() {
    private val response = MutableLiveData<String>()
    private val data = MutableLiveData<ResultCategoryPreview?>()

    fun getResponse() : MutableLiveData<String>{
        return response
    }
    fun getData(): MutableLiveData<ResultCategoryPreview?>{
        return data
    }

    fun getAllCategories(){
        mDisposable.add(repo.getAllCategories()
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                 response.postValue(it.msg)
                 data.postValue(it.data)
            },{
                response.postValue(it.message)
                data.postValue(null)
            }))
    }
}