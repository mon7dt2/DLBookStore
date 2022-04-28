package com.base.mvvmbasekotlin.network

import com.base.mvvmbasekotlin.models.response.AuthResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface) {

    fun adminLogin(encodedString: String): Single<AuthResponse> {
        return apiInterface.adminLogin(encodedString)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}