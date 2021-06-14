package com.example.quizza

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.quizza.entities.Match
import com.example.quizza.entities.Question
import kotlinx.android.synthetic.main.info_dialog.view.*

class InfoDialog(question: Question): DialogFragment() {

    private var myQuestion = question

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.info_dialog, container, false)

        rootView.bgInfoDialog.setBackgroundResource(AppColor.getBackgroundColor())

        rootView.tvQuestionInfo.text = myQuestion.getQuestion()
        rootView.tvAnswerInfoA.text = "1. " + myQuestion.getCorrectAnswer()
        rootView.tvAnswerInfoB.text = "2. " + myQuestion.getWrongAnswers(0)
        rootView.tvAnswerInfoC.text = "3. " + myQuestion.getWrongAnswers(1)
        rootView.tvAnswerInfoD.text = "4. " + myQuestion.getWrongAnswers(2)

        return rootView
    }

}