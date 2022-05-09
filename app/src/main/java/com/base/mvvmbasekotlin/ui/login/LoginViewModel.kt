package com.base.mvvmbasekotlin.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.network.Repository
import com.tencent.mmkv.MMKV
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var repo: Repository) : BaseViewModel(){

    var kv = MMKV.defaultMMKV()!!
    private val response = MutableLiveData<String>()
    fun getResponse() : MutableLiveData<String>{
        return response
    }

    fun login(encodedString : String){
        mDisposable.add(repo.adminLogin(encodedString)
            .doOnSubscribe{
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe({
                Log.d("myLog", it.toString())
                response.postValue("HTTP OK")
            }, {
                it.message?.let { it1 -> Log.d("myLog", it1) }
                response.postValue(it.message)
            })
        )
    }

    fun saveLoginSession(encodedString: String){
        kv.encode("jwt", encodedString)
    }

    fun checkLoginSession() : Boolean{
        return !kv.decodeString("jwt").equals("")
    }
}