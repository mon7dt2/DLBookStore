package com.base.mvvmbasekotlin.ui.dashboard.detail

import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardDetailViewModel @Inject constructor(val repo: Repository) : BaseViewModel(){

}