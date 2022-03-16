package com.larvata.qrcodetest.ui.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object RequestPermission {

    val requestPermission = 6666

    val permissions = arrayOf(
        android.Manifest.permission.CAMERA
    )

    fun checkPermissions(context: Context): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

}