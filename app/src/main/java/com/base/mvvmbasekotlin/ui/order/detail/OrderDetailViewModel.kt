package com.base.mvvmbasekotlin.ui.order.detail

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.OkResponse
import com.base.mvvmbasekotlin.models.response.OrderDetailResponse
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(val repo: Repository): BaseViewModel(){
    private val data = MutableLiveData<OrderDetailResponse?>()
    private val response = MutableLiveData<OkResponse?>()

    fun getResponse(): MutableLiveData<OkResponse?>{
        return response
    }
    fun getData(): MutableLiveData<OrderDetailResponse?>{
        return data
    }

    fun getOrderDetail(encodedString: String, orderID: String){
        mDisposable.add(repo.getOrderDetail(encodedString, orderID)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe({
                data.postValue(it)
            }, {
                data.postValue(null)
            }))
    }

    fun updateOrderDetail(encodedString: String, orderID: String, state: Int){
        mDisposable.add(repo.updateOrderState(encodedString, orderID, state)
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