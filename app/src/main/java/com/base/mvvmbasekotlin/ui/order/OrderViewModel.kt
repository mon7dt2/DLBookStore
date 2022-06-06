package com.base.mvvmbasekotlin.ui.order

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.OrdersResponse
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(val repo: Repository): BaseViewModel(){

    private val response = MutableLiveData<OrdersResponse?>()

    fun getResponse() : MutableLiveData<OrdersResponse?>{
        return response
    }

    fun getAllOrders(encodedString: String){
        mDisposable.add(repo.getAllOrders(encodedString)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe({
                response.postValue(it)
            }, {
                response.postValue(null)
            }))
    }

    fun getOrderByState(encodedString: String, state: Int){
        mDisposable.add(repo.getOrderByState(encodedString, state)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe({
                response.postValue(it)
            }, {
                response.postValue(null)
            }))
    }
}