package com.base.mvvmbasekotlin.network

import com.base.mvvmbasekotlin.models.response.AuthResponse
import io.reactivex.Single
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {

    @POST("/api/auths/staff/login/")
    fun staffLogin(@Header("Authorization") encodedString : String): Single<AuthResponse>

    @POST("/api/auths/admin/login/")
    fun adminLogin(@Header("Authorization") encodedString : String): Single<AuthResponse>

}
