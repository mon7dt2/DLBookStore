package com.base.mvvmbasekotlin.models.data

import java.io.Serializable

data class OrderDetail(val id: String, val order: Order, val book: ProductPreview, val quantity: Int, val total: Double): Serializable
