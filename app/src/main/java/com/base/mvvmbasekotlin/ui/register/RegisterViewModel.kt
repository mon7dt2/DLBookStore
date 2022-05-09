package com.base.mvvmbasekotlin.ui.register

import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repo: Repository): BaseViewModel(){

}