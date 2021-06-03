package com.example.quizza.entities

import com.google.gson.Gson
import kotlin.random.Random

class Question(category: Int) {
    private var myCategoryIndex = 0
    private lateinit var question:  String
    private lateinit var rightAnswer: String
    private lateinit var wrongAnswers: MutableList<WrongAnswer>

    init{
        //Qua dentro ci va il codice con la libreria GSON
        //fill myCategory
        myCategoryIndex = category

        /*val temp = Random.nextInt(1, 3)
        val filename = categories[myCategoryIndex] + "_questions/question" + temp.toString() + ".json"*/


        //fill myQuestion
        //fill myCorrectAnswer
        //fill myWrongAnswers
    }

    fun checkAnswer(str: String): Boolean{
        return if (str == rightAnswer) true else false
    }

    fun getCorrectAnswer(): String {
        return rightAnswer
    }

    fun getWrongAnswers(): MutableList<WrongAnswer>{
        return wrongAnswers
    }
}

class WrongAnswer {
    private lateinit var wrong: String

    public fun getAnswer(): String {
        return wrong
    }
}