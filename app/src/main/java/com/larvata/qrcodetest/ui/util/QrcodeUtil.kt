package com.larvata.qrcodetest.ui.util

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

object QrcodeUtil {

    fun registerResult(fragment: Fragment, result: (mResult : ScanIntentResult) -> Unit): ActivityResultLauncher<ScanOptions> {
        return fragment.registerForActivityResult(ScanContract()) {
            result(it)
        }
    }

    fun customScanOptions(): ScanOptions {
        return ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setBeepEnabled(true)
            setOrientationLocked(true)
            setPrompt("Scan QR code")
        }
    }

}