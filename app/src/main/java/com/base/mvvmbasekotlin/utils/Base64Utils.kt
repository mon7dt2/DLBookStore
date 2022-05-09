package com.base.mvvmbasekotlin.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*


object Base64Utils {
    /**
     * Phương thức decode string
     * @param base64EncodedString string cần decode
     * @return string đã được decode
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun decode(base64EncodedString: String?): String? {
        val bytes: ByteArray = Base64.getDecoder().decode(base64EncodedString)
        return String(bytes)
    }

    /**
     * Phương thức encode string
     * @param value string cần encode
     * @return string đã được encode
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun encode(value: String): String? {
        val bytes: ByteArray = Base64.getEncoder().encode(value.toByteArray())
        return String(bytes)
    }
}