package com.base.mvvmbasekotlin.models.response

import com.base.mvvmbasekotlin.models.data.OrderDetail

data class OrderDetailResponse(val code: Int, val msg: String, val data: List<OrderDetail>)
