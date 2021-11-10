package com.base.mvvmbasekotlin.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.base.mvvmbasekotlin.R

/**
 * Created by Mon7 on 9/1/2021.
 *
 */
class LoadingDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.loading_dialog)
        setCanceledOnTouchOutside(false)
    }
}