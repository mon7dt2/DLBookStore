package com.base.mvvmbasekotlin.network

import com.base.mvvmbasekotlin.models.request.StaffRegisterRequest
import com.base.mvvmbasekotlin.models.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
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

    @Multipart
    @POST("/api/category")
    fun addCateGory(@Header("Authorization") encodedString : String,
                    @Part("displayName") displayName : String,
                    @Part cover: MultipartBody.Part): Single<CategoryResponse>

    @Multipart
    @PATCH("/api/category/{categoryID}")
    fun updateCateGory(@Header("Authorization") encodedString : String,
                       @Path("categoryID") categoryID: Long,
                       @Part("displayName") displayName : String,
                       @Part cover: MultipartBody.Part?): Single<UpdateOkResponse>

    @DELETE("/api/category/{categoryId}")
    fun deleteCategory(@Header("Authorization") encodedString : String,
                       @Path("categoryId")id: Long): Single<OkResponse>

    @GET("/api/admins/customer")
    fun getAllCustomer(@Header("Authorization") encodedString : String): Single<CustomerResponse>
}
