package com.example.quizza.entities

class Question() {
    private var myCategoryIndex = 0
    private lateinit var question:  String
    private lateinit var rightAnswer: String
    private lateinit var wrongAnswers: MutableList<WrongAnswer>

    init{
        //Qua dentro ci va il codice con la libreria GSON
        //fill myCategory
        //myCategoryIndex = category
        println("\nCREATA QUESTION")
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

    fun getInfo(): String {
        return "\n\nquestion: $question \nright answer: $rightAnswer \nwrong answer: " +
                "${wrongAnswers[0].getAnswer()}, ${wrongAnswers[1].getAnswer()}, ${wrongAnswers[2].getAnswer()}"
    }
}

class WrongAnswer {
    private lateinit var wrong: String

    fun getAnswer(): String {
        return wrong
    }
}