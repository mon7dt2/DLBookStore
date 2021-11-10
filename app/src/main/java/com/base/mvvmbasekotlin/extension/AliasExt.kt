package com.base.mvvmbasekotlin.extension

import androidx.lifecycle.MutableLiveData
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.base.entity.BaseListLoadMoreResponse
import com.base.mvvmbasekotlin.base.entity.BaseListResponse
import com.base.mvvmbasekotlin.base.entity.BaseObjectResponse

typealias ObjectResponse<T> = MutableLiveData<BaseObjectResponse<T>>
typealias ListResponse<T> = MutableLiveData<BaseListResponse<T>>
typealias ListLoadMoreResponse<T> = MutableLiveData<BaseListLoadMoreResponse<T>>

typealias AndroidColors = android.R.color
typealias ProjectColors = R.color
