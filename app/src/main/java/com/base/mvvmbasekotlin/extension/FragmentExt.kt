package com.base.mvvmbasekotlin.extension

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.base.mvvmbasekotlin.base.permission.PermissionHelper

fun Fragment.openGallery(requestCode : Int){
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent,requestCode)
}

fun Fragment.goToGallery(listener:() -> Unit){
    PermissionHelper().withFragment(this)
        .check(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .onSuccess {
            listener()
        }
        .onDenied {
            Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
        }
        .onNeverAskAgain {
        }
        .run()
}