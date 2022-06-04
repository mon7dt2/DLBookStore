package com.base.mvvmbasekotlin.models.result

import com.base.mvvmbasekotlin.models.data.ProviderPreview

data class ResultProviderPreview(val results: ArrayList<ProviderPreview>, val pageIndex: Int,
                                 val pageSize: Int, val totalPage: Int, val totalItem: Int)
