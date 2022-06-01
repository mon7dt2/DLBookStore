package com.base.mvvmbasekotlin.ui.dashboard

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repo: Repository) : BaseViewModel() {

    private val response = MutableLiveData<String>()

    fun getResponse() : MutableLiveData<String>{
        return response
    }
}