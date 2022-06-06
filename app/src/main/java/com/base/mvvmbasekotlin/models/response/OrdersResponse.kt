package com.base.mvvmbasekotlin.models.response

import com.base.mvvmbasekotlin.models.data.Order

data class OrdersResponse(val code: Int, val msg: String, val data: List<Order>)
