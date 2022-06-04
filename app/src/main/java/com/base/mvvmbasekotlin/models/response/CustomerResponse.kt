package com.base.mvvmbasekotlin.models.response

import com.base.mvvmbasekotlin.models.data.CustomerPreview

data class CustomerResponse(val code: Int, val msg: String, val data: ArrayList<CustomerPreview>)