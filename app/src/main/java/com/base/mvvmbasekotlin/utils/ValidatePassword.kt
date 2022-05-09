package com.base.mvvmbasekotlin.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

object ValidatePassword {
    private val VALID_PASSWORD_REGEX: Pattern =
        Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")

    fun validate(password: String?): Boolean {
        val matcher: Matcher = VALID_PASSWORD_REGEX.matcher(password)
        return matcher.find()
    }
}