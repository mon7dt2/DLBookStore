package com.base.mvvmbasekotlin.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Mon7 on 4/28/2022.
 *
 */
class ValidatePhone {
    private val VALID_VN_PHONE_REGEX: Pattern =
        Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b", Pattern.CASE_INSENSITIVE)

    fun validate(phone: String?): Boolean {
        val matcher: Matcher = VALID_VN_PHONE_REGEX.matcher(phone)
        return matcher.find()
    }
}