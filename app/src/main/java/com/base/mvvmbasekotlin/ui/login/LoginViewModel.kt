package com.base.mvvmbasekotlin.ui.login

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.network.Repository
import com.base.mvvmbasekotlin.utils.Base64Utils
import com.base.mvvmbasekotlin.utils.MMKVHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var repo: Repository) : BaseViewModel(){

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
                response.postValue("HTTP OK")
                MMKVHelper.getInstance().encode("fullName", it.data.fullName)
                MMKVHelper.getInstance().encode("id", it.data.id)
                MMKVHelper.getInstance().encode("avatarUrl", it.data.avatarUrl)
                MMKVHelper.getInstance().encode("accountId", it.data.accountID)
                MMKVHelper.getInstance().encode("dateOfBirth", it.data.dateOfBirth)
                MMKVHelper.getInstance().encode("email", it.data.email)
                MMKVHelper.getInstance().encode("address", it.data.address)
            }, {
                response.postValue(it.message)
            })
        )
    }

    fun saveLoginSession(encodedString: String){
        MMKVHelper.getInstance().encode("jwt", encodedString)
    }

    fun checkLoginSession() : Boolean{
        return MMKVHelper.getInstance().decodeString("jwt") != null || !MMKVHelper.getInstance().decodeString("jwt").equals("")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLoginSession(): List<String>? {
        return if (checkLoginSession()) {
            val string = MMKVHelper.getInstance().decodeString("jwt", "")
            if(string != null && string != ""){
                val jwt1 = string.split(" ")
                val jwt = Base64Utils.decode(jwt1[1])!!
                jwt.split(":")
            } else {
                null
            }
        } else {
            null
        }
    }

    fun removeLoginSession(){
        MMKVHelper.getInstance().removeValueForKey("jwt")
    }
}