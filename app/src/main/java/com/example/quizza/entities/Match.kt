package com.example.quizza.entities

import kotlin.random.Random

class Match {

    companion object{
        const val TIMER_TO_SCORE_MULTIPLIER = 10   //30 seconds equals to 30*10 = 300 pts
    }

    private var questions = mutableListOf<Question>()
    private var questionsTimeLeft = mutableListOf<Int>(0,0,0,0)
    private var isGuessedRight = mutableListOf<Boolean>(false, false, false, false)

    private var categoriesIndex = mutableListOf<Int>()
    private var currentQuestion = 0

    init{
        //we're generating four DIFFERENT random numbers
        //so we'll have 1 category - 1 question
        categoriesIndex = generateRandomNumbers(4, 6)

        for(i in 0..4)
            questions.add(Question(categoriesIndex[i]))
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

    private fun generateRandomNumbers(numOfRandomNumbers: Int, bound: Int): MutableList<Int>{
        var randomNumbers = mutableListOf<Int>()

        var i = 0
        while(i <= numOfRandomNumbers){
            var num = Random.nextInt(0 , bound)
            if(num in randomNumbers)
                continue
            else
                randomNumbers.add(num)

            i++
        }
        return randomNumbers
    }
}