package com.example.quizza.entities

import java.io.Serializable

class Match (questions: MutableList<Question>): Serializable {

    companion object{
        const val TIMER_TO_SCORE_MULTIPLIER = 10   //30 seconds equals to 30*10 = 300 pts
    }

    private var questions = mutableListOf<Question>()
    private var questionsTimeLeft = mutableListOf<Int>(0,0,0,0)
    private var isGuessedRight = mutableListOf<Boolean>(false, false, false, false)
    private var currentQuestionIndex = 0

    init{
        this.questions = questions
    }

    fun setScore(timer: Int){
        questionsTimeLeft[currentQuestionIndex] = timer * TIMER_TO_SCORE_MULTIPLIER
    }

    fun checkAnswer(answer: String): Boolean{
        val result = questions[currentQuestionIndex].checkAnswer(answer)
        if(result == false)
            questionsTimeLeft[currentQuestionIndex] = 0   // 0 pts if user wrongly answers to the question

        currentQuestionIndex++
        return result
    }

    fun loadNextQuestion(): Question? {
        if(currentQuestionIndex == 4)
            return null

        return questions[currentQuestionIndex]
    }
}