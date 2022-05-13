package com.base.mvvmbasekotlin.ui.register

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.request.StaffRegisterRequest
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repo: Repository): BaseViewModel(){

    private val response = MutableLiveData<String>()

    fun getResponse(): MutableLiveData<String>{
        return response
    }

    fun registerNewStaff(encodedString: String, body: StaffRegisterRequest): Boolean {
        return if (body.fullName != "" && body.address != "" && body.email != "" && body.phone != ""
            && body.dateOfBirth != ""
        ) {
            mDisposable.add(repo.staffRegister(encodedString, body)
                .doOnDispose {
                    loading.postValue(true)
                }.doFinally {
                    loading.postValue(false)
                }.subscribe({
                    response.postValue("HTTP OK")
                }, {
                    response.postValue(it.message)
                })
            )
            true
        } else false
    }
}