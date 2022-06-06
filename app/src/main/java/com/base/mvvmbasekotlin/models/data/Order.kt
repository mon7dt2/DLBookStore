package com.base.mvvmbasekotlin.models.data

import java.io.Serializable

data class Order(val id: String, val searchKey: String, val customer: CustomerPreview, val totalPrice: Double,
                    val createdAt: Long, val updatedAt: Long, val orderStatus: Int, val address: String): Serializable
