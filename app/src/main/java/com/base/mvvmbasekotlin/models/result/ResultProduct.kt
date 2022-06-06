package com.base.mvvmbasekotlin.models.result

import com.base.mvvmbasekotlin.models.data.ProductPreview

data class ResultProduct(val results: ArrayList<ProductPreview>, val pageIndex: Int,
                         val pageSize: Int, val totalPage: Int, val totalItem: Int)
