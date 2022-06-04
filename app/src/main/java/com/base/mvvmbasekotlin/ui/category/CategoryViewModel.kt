package com.base.mvvmbasekotlin.ui.category

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.OkResponse
import com.base.mvvmbasekotlin.models.result.ResultCategoryPreview
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(val repo: Repository): BaseViewModel() {
    private val response = MutableLiveData<String>()
    private val data = MutableLiveData<ResultCategoryPreview?>()
    private val deleteStatus = MutableLiveData<OkResponse?>()

    fun getResponse() : MutableLiveData<String>{
        return response
    }
    fun getData(): MutableLiveData<ResultCategoryPreview?>{
        return data
    }

    fun getDeleteStatus(): MutableLiveData<OkResponse?>{
        return deleteStatus
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

    fun deleteCategory(encodedString: String, categoryID: Long){
        mDisposable.add(repo.deleteCategory(encodedString, categoryID)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                response.postValue(it.msg)
                deleteStatus.postValue(it)
            },{
                response.postValue(it.message)
                deleteStatus.postValue(null)
            }))
    }
}