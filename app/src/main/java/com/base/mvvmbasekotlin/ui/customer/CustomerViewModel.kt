package com.base.mvvmbasekotlin.ui.customer

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.CustomerResponse
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(val repo: Repository): BaseViewModel(){

    private val response = MutableLiveData<String>()
    private val data = MutableLiveData<CustomerResponse?>()

    fun getResponse() : MutableLiveData<String>{
        return response
    }
    fun getData(): MutableLiveData<CustomerResponse?>{
        return data
    }

    fun getAllCustomer(encodedString: String){
        mDisposable.add(repo.getAllCustomer(encodedString)
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
}