package com.base.mvvmbasekotlin.network

import com.base.mvvmbasekotlin.models.request.ProviderBodyRequest
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

    @GET("/api/admin/customer")
    fun getAllCustomer(@Header("Authorization") encodedString : String): Single<CustomerResponse>

    @GET("/api/provider")
    fun getAllProvider(@Header("Authorization") encodedString : String): Single<ProviderResponse>

    @POST("/api/provider")
    fun addProvider(@Header("Authorization") encodedString : String,
                    @Body body: ProviderBodyRequest): Single<SingleProviderResponse>
    @PATCH("/api/provider/{providerId}")
    fun updateProvider(@Header("Authorization") encodedString : String,
                    @Path("providerId") id: Int,
                     @Body body: ProviderBodyRequest): Single<UpdateOkResponse>
    @DELETE("/api/provider/{providerId}")
    fun deleteProvider(@Header("Authorization") encodedString : String,
                     @Path("providerId") id: Int): Single<UpdateOkResponse>

    @GET("/api/product")
    fun getAllProducts(): Single<ProductListResponse>

    @Multipart
    @POST("/api/product")
    fun addProduct(@Header("Authorization") encodedString : String,
                    @Part("displayName") displayName: String,
                    @Part("description") description: String,
                    @Part("price") price: Float,
                    @Part("quantity") quantity: Int,
                    @Part("author") author: String,
                    @Part("publisher") publisher: String,
                    @Part("categoryID") categoryID: Long,
                    @Part("providerID") providerID: Long,
                    @Part cover: MultipartBody.Part?): Single<OkResponse>

    @Multipart
    @PATCH("/api/product/{productId}")
    fun updateProduct(@Header("Authorization") encodedString : String,
                    @Path("productId") id: String,
                   @Part("displayName") displayName: String,
                   @Part("description") description: String?,
                   @Part("price") price: Float,
                   @Part("quantity") quantity: Int,
                   @Part("author") author: String,
                   @Part("publisher") publisher: String,
                   @Part("categoryID") categoryID: Long,
                   @Part("providerID") providerID: Long,
                   @Part cover: MultipartBody.Part?): Single<OkResponse>

    @DELETE("/api/product/{bookID}")
    fun deleteProduct(@Header("Authorization") encodedString : String,
                      @Path("bookID") id: String): Single<OkResponse>

    @GET("/api/orders")
    fun getAllOrder(@Header("Authorization") encodedString : String): Single<OrdersResponse>

    @GET("/api/order/{orderID}")
    fun getOrderDetail(@Header("Authorization") encodedString : String,
                        @Path("orderID") id: String): Single<OrderDetailResponse>

    @Multipart
    @PUT("/api/order/{orderID}")
    fun updateOrderStatus(@Header("Authorization") encodedString : String,
                       @Path("orderID") id: String,
                       @Part("state") state: Int ): Single<OkResponse>

    @GET("/api/order/state/{state}")
    fun getOrderByState(@Header("Authorization") encodedString : String,
                       @Path("state") state: Int): Single<OrdersResponse>

    @GET("/api/admin/total/category")
    fun getTotalCategory(@Header("Authorization") encodedString : String): Single<TotalCategoryResponse>
}
