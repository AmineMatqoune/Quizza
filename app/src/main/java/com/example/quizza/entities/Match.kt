package com.example.quizza.entities

import kotlin.random.Random

class Match (jsonFiles: MutableList<String>) {

    companion object{
        const val TIMER_TO_SCORE_MULTIPLIER = 10   //30 seconds equals to 30*10 = 300 pts
    }

    private var questions = mutableListOf<Question>()
    private var questionsTimeLeft = mutableListOf<Int>(0,0,0,0)
    private var isGuessedRight = mutableListOf<Boolean>(false, false, false, false)
    private var currentQuestion = 0

    init{
        //we're generating four DIFFERENT random numbers
        //so we'll have 1 category - 1 question

        //avremo un array composto da 4 stringhe che sarebbero i file json, e per ognuno di essi ci creiamo un oggetto domanda e lo carichiamo tramite gson

    }

    fun setScore(timer: Int){
        questionsTimeLeft[currentQuestion] = timer * TIMER_TO_SCORE_MULTIPLIER
    }

    fun checkAnswer(answer: String): Boolean{
        val result = questions[currentQuestion].checkAnswer(answer)
        if(result == false)
            questionsTimeLeft[currentQuestion] = 0   // 0 pts if user wrongly answers to the question

        currentQuestion++
        return result
    }

    fun loadNextQuestion(): Question? {
        if(currentQuestion == 4)
            return null

        return questions[currentQuestion]
    }
}