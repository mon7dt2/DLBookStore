package com.base.mvvmbasekotlin.ui.provider

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.ProviderResponse
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProviderViewModel @Inject constructor(val repo: Repository): BaseViewModel(){

    private val response = MutableLiveData<ProviderResponse?>()

    fun getResponse() : MutableLiveData<ProviderResponse?>{
        return response
    }

    fun getAllProvider(encodedString: String){
        mDisposable.add(repo.getAllProvider(encodedString)
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