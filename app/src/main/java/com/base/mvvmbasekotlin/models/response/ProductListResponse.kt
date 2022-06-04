package com.base.mvvmbasekotlin.models.response

import com.base.mvvmbasekotlin.models.data.ProductPreview

data class ProductListResponse(val code: Int, val msg: String, val data: List<ProductPreview>)
