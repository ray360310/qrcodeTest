package com.larvata.qrcodetest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.larvata.qrcodetest.R
import com.larvata.qrcodetest.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private val mBinding: FragmentResultBinding by lazy {
        FragmentResultBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initialView()
        return mBinding.root
    }

    private fun initialView(){
        arguments?.let {
            ResultFragmentArgs.fromBundle(it).let { mData ->
                mBinding.fragmentResultResultTxw.text = String.format(
                    getString(R.string.fragment_result_result_txw),
                    mData.userName,
                    mData.totalQuestions,
                    mData.correctQuestions,
                    mData.resultStr
                )
            }
        }
    }

}