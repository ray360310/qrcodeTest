package com.larvata.qrcodetest.ui.util

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object RegisterResultHelper {

    fun registerResult(fragment: Fragment, result: (mResult : ActivityResult) -> Unit): ActivityResultLauncher<Intent> {
        return fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result(it)
        }
    }

    fun registerResult(activity: AppCompatActivity, result: (mResult : ActivityResult) -> Unit): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result(it)
        }
    }

}