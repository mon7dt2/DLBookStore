package com.base.mvvmbasekotlin.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


open class BaseViewModel : ViewModel() {

    protected var mDisposable = CompositeDisposable()

    var loading = MutableLiveData<Boolean>()

    fun getLoadingStatus(): MutableLiveData<Boolean> {
        return loading
    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }
}
