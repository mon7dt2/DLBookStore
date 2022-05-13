package com.base.mvvmbasekotlin.models.request

data class StaffRegisterRequest(val fullName: String, val phone: String,
                                val dateOfBirth: String, val gender: Int,
                                val email: String, val address: String)
