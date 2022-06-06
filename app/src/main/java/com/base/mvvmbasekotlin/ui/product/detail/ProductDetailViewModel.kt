package com.base.mvvmbasekotlin.ui.product.detail

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.base.BaseViewModel
import com.base.mvvmbasekotlin.models.response.OkResponse
import com.base.mvvmbasekotlin.models.result.ResultCategoryPreview
import com.base.mvvmbasekotlin.models.result.ResultProviderPreview
import com.base.mvvmbasekotlin.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(val repo: Repository): BaseViewModel(){

    private val dataCategory = MutableLiveData<ResultCategoryPreview?>()
    private val dataProvider = MutableLiveData<ResultProviderPreview?>()
    private val dataResponse = MutableLiveData<OkResponse?>()
    var coverPath: String?=null

    fun getDataCategory(): MutableLiveData<ResultCategoryPreview?>{
        return dataCategory
    }

    fun getDataProvider(): MutableLiveData<ResultProviderPreview?>{
        return dataProvider
    }

    fun getDataResponse(): MutableLiveData<OkResponse?>{
        return dataResponse
    }

    fun getAllCategories(){
        mDisposable.add(repo.getAllCategories()
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                dataCategory.postValue(it.data)
            },{
                dataCategory.postValue(null)
            }))
    }

    fun getAllProvider(encodedString: String){
        mDisposable.add(repo.getAllProvider(encodedString)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                dataProvider.postValue(it.data)
            },{
                dataProvider.postValue(null)
            }))
    }

    fun addProduct(encodedString: String, displayName: String, description: String,
                   price: Float, quantity: Int, author: String, publisher: String,
                   categoryID: Long, providerID: Long, avatar: MultipartBody.Part){
        mDisposable.add(repo.addProduct(encodedString, displayName, description, price,
                                quantity,author, publisher, categoryID, providerID, avatar)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe ({
                dataResponse.postValue(it)
            },{
                dataResponse.postValue(null)
            }))
    }

    fun updateProduct(encodedString: String, id: String, displayName: String, description: String,
                      price: Float, quantity: Int, author: String, publisher: String,
                      categoryID: Long, providerID: Long, avatar: MultipartBody.Part?) {
        mDisposable.add(repo.updateProduct(encodedString, id, displayName, description, price,
            quantity, author, publisher, categoryID, providerID, avatar
        )
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe({
                dataResponse.postValue(it)
            }, {
                dataResponse.postValue(null)
            })
        )
    }

    fun deleteProduct(encodedString: String, id: String){
        mDisposable.add(repo.deleteProduct(encodedString, id)
            .doOnDispose {
                loading.postValue(true)
            }.doFinally {
                loading.postValue(false)
            }.subscribe({
                dataResponse.postValue(it)
            }, {
                dataResponse.postValue(null)
            })
        )
    }

}