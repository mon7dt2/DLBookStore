package com.base.mvvmbasekotlin.base.entity

data class BaseError(var error: String, var code: Int) : Exception(error)