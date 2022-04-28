package com.base.mvvmbasekotlin.ui.login

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var repo: Repository) : BaseViewModel(){

    private val failResponse = MutableLiveData<String?>()
    fun getFailResponse() : MutableLiveData<String?>{
        return failResponse;
    }
}