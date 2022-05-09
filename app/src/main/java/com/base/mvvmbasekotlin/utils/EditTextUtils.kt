package com.base.mvvmbasekotlin.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView

object EditTextUtils {
    fun setEditText(edt: EditText, img: ImageView) {
        //hide cross
        if (edt.text.toString() == "") {
            img.visibility = View.GONE
            img.isEnabled = false
            img.isClickable = false
        }
        edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (edt.text.toString() == "") {
                    img.visibility = View.GONE
                    img.isEnabled = false
                    img.isClickable = false
                } else {
                    img.visibility = View.VISIBLE
                    img.isEnabled = true
                    img.isClickable = true
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        //clear text
        img.setOnClickListener { edt.setText("") }
    }
}