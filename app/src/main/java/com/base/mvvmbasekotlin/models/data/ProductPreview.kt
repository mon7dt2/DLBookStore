package com.base.mvvmbasekotlin.models.data

import java.io.Serializable

data class ProductPreview(val id: String, val displayName: String, val description: String, val avatarUrl: String,
                        val price: Double, val quantity: Int, val author: String, val publisher: String, val isDeleted: Int,
                        val category: Category, val provider: ProviderPreview): Serializable
