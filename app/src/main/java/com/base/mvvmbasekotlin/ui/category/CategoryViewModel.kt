package com.base.mvvmbasekotlin.ui.category

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repo: Repository): BaseViewModel() {
    private val response = MutableLiveData<String>()

    fun getResponse() : MutableLiveData<String>{
        return response
    }

    fun getAllCategories(){
        mDisposable.add(repo.getAllCategories()
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