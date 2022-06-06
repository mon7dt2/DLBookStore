package com.base.mvvmbasekotlin.models.response

import com.base.mvvmbasekotlin.models.result.ResultProduct

data class ProductListResponse(val code: Int, val msg: String, val data: ResultProduct)
