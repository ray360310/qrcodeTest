package com.larvata.qrcodetest.ui

import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.common.util.concurrent.ListenableFuture
import com.larvata.qrcodetest.R
import com.larvata.qrcodetest.databinding.FragmentQrcodeBinding
import com.larvata.qrcodetest.ui.dialog.QuestionsDialog
import com.larvata.qrcodetest.ui.util.QrCodeAnalyzer
import com.larvata.qrcodetest.ui.util.RequestPermission
import java.util.concurrent.Executors

class QrcodeFragment : Fragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val cameraExecutor = Executors.newSingleThreadExecutor()

    private val mBinding: FragmentQrcodeBinding by lazy {
        FragmentQrcodeBinding.inflate(layoutInflater)
    }

    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraInfo: CameraInfo
    private lateinit var cameraControl: CameraControl

    private var isFlashLightOpened = false
    private var formerResult = ""

    private val cameraSelector: CameraSelector by lazy {
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }

    private val preview: Preview by lazy {
        Preview.Builder().apply {
            setTargetResolution(Size(mBinding.fragmentQrcodePreviewView.width, mBinding.fragmentQrcodePreviewView.height))
        }.build()
    }

    private val imageAnalysis: ImageAnalysis by lazy {
        ImageAnalysis.Builder()
            .setTargetResolution(Size(mBinding.fragmentQrcodePreviewView.width, mBinding.fragmentQrcodePreviewView.height))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, QrCodeAnalyzer { qrResult ->
                    if (qrResult.text != formerResult) {
                        mBinding.fragmentQrcodePreviewView.post {
                            Toast.makeText(requireContext(), "Barcode scanned: ${qrResult.text}", Toast.LENGTH_SHORT).show()
                            Log.d("QRCodeAnalyzer", "Barcode scanned: ${qrResult.text}")
                            QuestionsDialog(
                                requireContext(),
                                questionsCallback = { questionNumber, resultStr ->
                                    arguments?.let { mBundle ->
                                        findNavController().navigate(
                                            QrcodeFragmentDirections.actionQrcodeFragmentToResultFragment(
                                                QrcodeFragmentArgs.fromBundle(mBundle).userName,
                                                1,
                                                1,
                                                "請問老闆該幫員工加薪了嗎？ ${resultStr}"
                                            )
                                        )
                                    }
                                },
                                "請問老闆該幫員工加薪了嗎？",
                                "對",
                                "沒錯"
                            ).apply {
                                lifecycle.addObserver(this)
                                show()
                            }
                        }
                        formerResult = qrResult.text
                    }
                })
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        if (RequestPermission.checkPermissions(requireContext())) {
            cameraProvider()
        }
        return mBinding.root
    }

    private fun cameraProvider(){
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()
                startCamera(cameraProvider)
            },
            ContextCompat.getMainExecutor(requireContext()))
    }

    private fun startCamera(cameraProvider: ProcessCameraProvider){
        cameraProvider.unbindAll()
        val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
        cameraInfo = camera.cameraInfo
        cameraControl = camera.cameraControl
        preview.setSurfaceProvider(mBinding.fragmentQrcodePreviewView.surfaceProvider)

        mBinding.fragmentQrcodeFlashlightImgbtn.apply {
            if (::cameraInfo.isInitialized && ::cameraControl.isInitialized) {
                if (cameraInfo.hasFlashUnit()) {
                    cameraControl.enableTorch(isFlashLightOpened)
                    setBackgroundResource(R.drawable.bg_btn_flashlight_enabled)
                    this.setOnClickListener {
                        if (isFlashLightOpened) {
                            setImageResource(R.drawable.ic_baseline_flashlight_off_24)
                        }else {
                            setImageResource(R.drawable.ic_baseline_flashlight_on_24)
                        }
                        isFlashLightOpened = !isFlashLightOpened
                        cameraControl.enableTorch(isFlashLightOpened)
                    }
                }else {
                    isEnabled = false
                    setBackgroundResource(R.drawable.bg_btn_flashlight_disabled)
                }
            }
        }
    }

}