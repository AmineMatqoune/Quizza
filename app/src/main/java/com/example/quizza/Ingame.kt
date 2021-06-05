package com.example.quizza

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizza.databinding.IngameBinding
import com.example.quizza.entities.Match
import com.example.quizza.entities.Question

class Ingame(): AppCompatActivity() {

    private lateinit var ingameBinding: IngameBinding
    private lateinit var currentQuestion: Question
    private lateinit var match: Match


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ingameBinding = IngameBinding.inflate(layoutInflater)
        setContentView(ingameBinding.root)

        match = intent.getSerializableExtra("match_instance") as Match

        //Mostra la prima domanda
        //fai partire il timer (ricorda che è il punteggio)
        //fai logica sui pulsanti se sono corrette le domande o meno (crei una mini funzione che usano tutti i pulsanti)

        setQuestionOnUI()
    }

    private fun setQuestionOnUI(){
        currentQuestion = match.loadNextQuestion()!!

        ingameBinding.tvQuestionIngame.text = currentQuestion.getQuestion()

        when(currentQuestion.getCategory()) {
            "0" -> { ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.geography_bg)
                ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.geography_bg)
                ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.geography_bg)
                ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.geography_bg) }

            "1" -> { ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.math_bg)
                ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.math_bg)
                ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.math_bg)
                ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.math_bg) }

            "2" -> { ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.music_bg)
                ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.music_bg)
                ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.music_bg)
                ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.music_bg) }

            "3" -> { ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.physics_bg)
                ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.physics_bg)
                ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.physics_bg)
                ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.physics_bg) }

            "4" -> { ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.science_bg)
                ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.science_bg)
                ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.science_bg)
                ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.science_bg) }

            "5" -> { ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.sport_bg)
                ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.sport_bg)
                ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.sport_bg)
                ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.sport_bg) }
        }

        //logica per scegliere dove mettere la risposta giusta e quelle sbagliate
    }

}