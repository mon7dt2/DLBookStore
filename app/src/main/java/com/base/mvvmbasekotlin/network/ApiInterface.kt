package com.base.mvvmbasekotlin.network

import com.base.mvvmbasekotlin.models.request.StaffRegisterRequest
import com.base.mvvmbasekotlin.models.response.AuthResponse
import com.base.mvvmbasekotlin.models.response.CategoryPreviewResponse
import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @POST("/api/auths/staff/login/")
    fun staffLogin(@Header("Authorization") encodedString : String): Single<AuthResponse>

    @POST("/api/auths/admin/login/")
    fun adminLogin(@Header("Authorization") encodedString : String): Single<AuthResponse>

    @POST("/api/auths/staff/register")
    fun staffRegister(@Header("Authorization") encodedString : String,
                        @Body body: StaffRegisterRequest): Single<AuthResponse>

    @POST("/api/auths/staff/verification/{staffID}")
    fun verifyStaff(@Header("Authorization") encodedString : String,
                    @Path("staffID") staffID: String): Single<AuthResponse>

    @GET("/api/category")
    fun getAllCategories(): Single<CategoryPreviewResponse>
}
