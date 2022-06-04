package com.base.mvvmbasekotlin.ui.provider.detail

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.request.ProviderBodyRequest
import com.base.mvvmbasekotlin.models.response.SingleProviderResponse
import com.base.mvvmbasekotlin.models.response.UpdateOkResponse
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProviderDetailViewModel @Inject constructor(val repo: Repository): BaseViewModel(){

    private val addResponse = MutableLiveData<SingleProviderResponse?>()
    private val updateOkResponse = MutableLiveData<UpdateOkResponse?>()

    fun getAddResponse(): MutableLiveData<SingleProviderResponse?> {
        return addResponse
    }

    fun getOkResponse(): MutableLiveData<UpdateOkResponse?>{
        return updateOkResponse
    }

    fun addProvider(encodedString: String, body: ProviderBodyRequest){
        mDisposable.add(repo.addProvider(encodedString, body)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                addResponse.postValue(it)
            },{
                addResponse.postValue(null)
            }))
    }

    fun updateProvider(encodedString: String, id: Int, body: ProviderBodyRequest){
        mDisposable.add(repo.updateProvider(encodedString, id, body)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                updateOkResponse.postValue(it)
            },{
                updateOkResponse.postValue(null)
            }))
    }

    fun deleteProvider(encodedString: String, id: Int){
        mDisposable.add(repo.deleteProvider(encodedString, id)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                updateOkResponse.postValue(it)
            },{
                updateOkResponse.postValue(null)
            }))
    }
}