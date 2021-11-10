package com.base.mvvmbasekotlin.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import java.io.File
import java.text.ParseException
import java.util.*

object Utils {
    @Throws(android.net.ParseException::class)
    fun formatDate(date: String?, initDateFormat: String?, endDateFormat: String?): String? {
        var initDate: Date? = null
        var parsedDate: String? = null
        var formatter: SimpleDateFormat? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                initDate = SimpleDateFormat(initDateFormat).parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            formatter = SimpleDateFormat(endDateFormat)
            parsedDate = formatter.format(initDate)
        }
        return parsedDate
    }

    fun deleteCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success =
                    deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }
}