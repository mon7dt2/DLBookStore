package com.base.mvvmbasekotlin.models.response

import com.base.mvvmbasekotlin.models.data.AuthData

data class AuthResponse(var code: String, var msg: String, var data: AuthData)
