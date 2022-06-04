package com.base.mvvmbasekotlin.models.response

import com.base.mvvmbasekotlin.models.result.ResultProviderPreview

data class ProviderResponse(val code: Int, val msg: String, val data: ResultProviderPreview)
