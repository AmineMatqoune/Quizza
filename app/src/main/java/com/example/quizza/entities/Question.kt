package com.example.quizza.entities

class Question(category: Int) {

    private var myCategoryIndex = 0
    private lateinit var myQuestion: String
    private lateinit var myCorrectAnswer: String
    private lateinit var myWrongAnswers: MutableList<String>

    init{
        //Qua dentro ci va il codice con la libreria GSON
        //fill myCategory
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