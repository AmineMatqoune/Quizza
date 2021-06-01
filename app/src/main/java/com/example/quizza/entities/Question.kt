package com.example.quizza.entities

import com.google.gson.Gson
import kotlin.random.Random

class Question(category: Int) {

    private val categories = listOf<String>("geography", "math", "music", "physics", "science", "sport")

    private var myCategoryIndex = 0
    private lateinit var myQuestion: String
    private lateinit var myCorrectAnswer: String
    private lateinit var myWrongAnswers: MutableList<String>

    init{
        //Qua dentro ci va il codice con la libreria GSON
        //fill myCategory
        myCategoryIndex = category

        val temp = Random.nextInt(1, 3)
        val filename = categories[myCategoryIndex] + "_questions/question" + temp.toString() + ".json"

        var gson = Gson()
        //var jsonString = Asset


        //fill myQuestion
        //fill myCorrectAnswer
        //fill myWrongAnswers
    }

    fun checkAnswer(str: String): Boolean{
        return if (str == myCorrectAnswer) true else false
    }

    fun getCorrectAnswer(): String {
        return myCorrectAnswer
    }

    fun getWrongAnswers(): MutableList<String>{
        return myWrongAnswers
    }





}