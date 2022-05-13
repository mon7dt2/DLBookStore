package com.base.mvvmbasekotlin.utils

import java.util.regex.Matcher
import java.util.regex.Pattern


object ValidateEmail {
    private val VALID_EMAIL_REGEX: Pattern =
        Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}\$")

    fun validate(email: String?): Boolean {
        val matcher: Matcher = VALID_EMAIL_REGEX.matcher(email)
        return matcher.find()
    }
}