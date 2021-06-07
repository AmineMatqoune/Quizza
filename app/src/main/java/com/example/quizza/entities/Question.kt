package com.example.quizza.entities

import java.io.Serializable

class Question(): Serializable {
    private lateinit var myCategoryIndex: String
    private lateinit var question:  String
    private lateinit var rightAnswer: String
    private lateinit var wrongAnswers: MutableList<WrongAnswer>

    fun checkAnswer(str: String): Boolean{
        return if (str == rightAnswer) true else false
    }

    fun getCategory(): String {
        return myCategoryIndex
    }

    fun getQuestion(): String {
        return question
    }

    fun getCorrectAnswer(): String {
        return rightAnswer
    }

    fun getWrongAnswers(index: Int): String{
        return if (index >= wrongAnswers.size) wrongAnswers.last().getAnswer() else wrongAnswers[index].getAnswer()
    }

    fun getInfo(): String {
        return "\n\nquestion: $question \nright answer: $rightAnswer \nwrong answer: " +
                "${wrongAnswers[0].getAnswer()}, ${wrongAnswers[1].getAnswer()}, ${wrongAnswers[2].getAnswer()}"
    }
}

class WrongAnswer: Serializable {
    private lateinit var wrong: String

    fun getAnswer(): String {
        return wrong
    }
}