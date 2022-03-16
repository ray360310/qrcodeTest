package com.larvata.qrcodetest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.larvata.qrcodetest.R
import com.larvata.qrcodetest.databinding.FragmentEnterBinding

class EnterFragment : Fragment() {

    private val mBinding: FragmentEnterBinding by lazy {
        FragmentEnterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initialView()
        return mBinding.root
    }

    private fun initialView(){
        mBinding.apply {
            fragmentEnterConfirmBtn.setOnClickListener {
                if (fragmentEnterNameTitleEdit.text.toString().isNotBlank()) {
                    findNavController().navigate(EnterFragmentDirections.actionEnterFragmentToQrcodeFragment(fragmentEnterNameTitleEdit.text.toString()))
                }else {
                    Toast.makeText(requireContext(), getString(R.string.toast_fragment_enter_please_enter_name), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}