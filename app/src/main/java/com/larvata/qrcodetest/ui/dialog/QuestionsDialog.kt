package com.larvata.qrcodetest.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleObserver
import com.larvata.qrcodetest.databinding.DialogQuestionsBinding

class QuestionsDialog(
    context: Context,
    private val questionsCallback: (answerNumber: Int, result: String) -> Unit,
    private val questionStr: String,
    private val ans1: String,
    private val ans2: String,
    private val ans3: String?= "",
    private val ans4: String?= ""
): Dialog(context), LifecycleObserver {

    private val mBinding: DialogQuestionsBinding by lazy {
        DialogQuestionsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        window?.apply {
            setBackgroundDrawable(ColorDrawable())
            setLayout(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
        }
        setCancelable(false)
        initialView()
    }

    private fun initialView(){
        mBinding.apply {
            dialogQuestionsQuestionTxw.text = questionStr
            if (ans3.isNullOrBlank()) {
                dialogQuestionsAnswer03Btn.visibility = View.GONE
            }
            if (ans4.isNullOrBlank()) {
                dialogQuestionsAnswer04Btn.visibility = View.GONE
            }
            dialogQuestionsAnswer01Btn.text = ans1
            dialogQuestionsAnswer02Btn.text = ans2
            dialogQuestionsAnswer03Btn.text = ans3
            dialogQuestionsAnswer04Btn.text = ans4
            dialogQuestionsAnswer01Btn.setOnClickListener {
                questionsCallback(1, ans1)
                dismiss()
            }
            dialogQuestionsAnswer02Btn.setOnClickListener {
                questionsCallback(2, ans2)
                dismiss()
            }
            dialogQuestionsAnswer03Btn.setOnClickListener {
                questionsCallback(3, ans3 ?: "")
                dismiss()
            }
            dialogQuestionsAnswer04Btn.setOnClickListener {
                questionsCallback(4, ans4 ?: "")
                dismiss()
            }
        }
    }

}