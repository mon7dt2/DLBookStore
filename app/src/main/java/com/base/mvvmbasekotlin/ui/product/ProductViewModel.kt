package com.base.mvvmbasekotlin.ui.product

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.ProductListResponse
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(val repo: Repository): BaseViewModel(){
    private val response = MutableLiveData<ProductListResponse?>()

    fun getResponse() : MutableLiveData<ProductListResponse?>{
        return response
    }

    fun getAllProducts(){
        mDisposable.add(repo.getAllProducts()
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                response.postValue(it)
            },{
                response.postValue(null)
            }))
    }
}