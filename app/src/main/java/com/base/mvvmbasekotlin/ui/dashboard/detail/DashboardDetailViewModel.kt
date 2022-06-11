package com.base.mvvmbasekotlin.ui.dashboard.detail

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.TotalCategoryResponse
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardDetailViewModel @Inject constructor(val repo: Repository) : BaseViewModel(){
        private val data = MutableLiveData<TotalCategoryResponse?>()

    fun getData(): MutableLiveData<TotalCategoryResponse?>{
        return data
    }

    fun getTotalCategory(encodedString: String){
        mDisposable.add(repo.getTotalCategory(encodedString)
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
}