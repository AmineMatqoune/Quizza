package com.example.quizza

import kotlin.random.Random

class Utils {

    companion object{
        fun generateRandomNumbers(numOfRandomNumbers: Int, lowerBound: Int, upperBound: Int, differentNumbers: Boolean): MutableList<Int>{
            var randomNumbers = mutableListOf<Int>()
            var i = 0

            if(differentNumbers){      //generate different random numbers
                while(i < numOfRandomNumbers){
                    var num = Random.nextInt(lowerBound , upperBound)
                    if(num in randomNumbers)
                        continue
                    else
                        randomNumbers.add(num)
                    i++
                }
            }else{                      //generate random numbers
                while(i < numOfRandomNumbers){
                    randomNumbers.add(Random.nextInt(lowerBound , upperBound))
                    i++
                }
            }

            return randomNumbers
        }

        fun permuteAnswers(correctAnswer: String, wrongAnswers0: String, wrongAnswers1: String, wrongAnswers2: String): List<String> {
            val randomIndexes = generateRandomNumbers(4 ,0 ,4 , true)
            val tempAnswers = listOf<String>(correctAnswer, wrongAnswers0, wrongAnswers1, wrongAnswers2)
            var randomAnswers = mutableListOf<String>()

            for (it in randomIndexes)
                randomAnswers.add(tempAnswers[it])
            return randomAnswers
        }
    }

}