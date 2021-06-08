package com.example.quizza

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quizza.databinding.IngameBinding
import com.example.quizza.entities.Match
import com.example.quizza.entities.Question

class Ingame(): AppCompatActivity() {

    private lateinit var ingameBinding: IngameBinding
    private var currentQuestion: Question? = null
    private lateinit var audioPlayer: MediaPlayer

    private lateinit var match: Match
    private var countDown = Match.QUESTION_MAX_TIMER  //30 seconds for each question
    private lateinit var countDownTimer: CountDownTimer
    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ingameBinding = IngameBinding.inflate(layoutInflater)
        setContentView(ingameBinding.root)
        match = intent.getSerializableExtra(intent.extras?.getInt("total_score").toString()) as Match
        setQuestionOnUI()
    }

    override fun onBackPressed() {}

    private fun setQuestionOnUI() {
        isClicked = false
        currentQuestion = match?.loadNextQuestion()

        if (currentQuestion == null) {
            val intentPostgame = Intent(applicationContext, Postgame::class.java)
            intentPostgame.putExtra(intent.extras?.getInt("total_score").toString(), match)
            intentPostgame.putExtras(intent.extras!!)
            startActivity(intentPostgame)

        } else {
            ingameBinding.tvQuestionIngame.text = currentQuestion!!.getQuestion()
            startTimer()

            when (currentQuestion!!.getCategory()) {
                "0" -> {
                    ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.geography_bg)
                    ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.geography_bg)
                    ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.geography_bg)
                    ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.geography_bg)
                }

                "1" -> {
                    ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.math_bg)
                    ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.math_bg)
                    ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.math_bg)
                    ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.math_bg)
                }

                "2" -> {
                    ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.music_bg)
                    ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.music_bg)
                    ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.music_bg)
                    ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.music_bg)
                }

                "3" -> {
                    ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.physics_bg)
                    ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.physics_bg)
                    ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.physics_bg)
                    ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.physics_bg)
                }

                "4" -> {
                    ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.science_bg)
                    ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.science_bg)
                    ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.science_bg)
                    ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.science_bg)
                }

                "5" -> {
                    ingameBinding.layoutAnswerA.setBackgroundResource(R.drawable.sport_bg)
                    ingameBinding.layoutAnswerB.setBackgroundResource(R.drawable.sport_bg)
                    ingameBinding.layoutAnswerC.setBackgroundResource(R.drawable.sport_bg)
                    ingameBinding.layoutAnswerD.setBackgroundResource(R.drawable.sport_bg)
                }
            }

            //permute answers in order to show them in a random position inside cardviews...
            val answersOrder = Utils.permuteAnswers(currentQuestion!!.getCorrectAnswer(), currentQuestion!!.getWrongAnswers(0), currentQuestion!!.getWrongAnswers(1), currentQuestion!!.getWrongAnswers(2))
            ingameBinding.tvAnswerA.text = answersOrder[0]
            ingameBinding.tvAnswerB.text = answersOrder[1]
            ingameBinding.tvAnswerC.text = answersOrder[2]
            ingameBinding.tvAnswerD.text = answersOrder[3]
        }
    }

    fun startTimer(){
        countDown = Match.QUESTION_MAX_TIMER
        countDownTimer = object: CountDownTimer(Match.QUESTION_MAX_TIMER * 1000 as Long, 1000){
            override fun onTick(millisUntilFinished: Long){
                ingameBinding.sbCounter.setProgress(--countDown)
                ingameBinding.tvCounter.text = countDown.toString() + "s"
            }

            override fun onFinish(){
                launchSound(false)
                match.checkAnswer("")   // "" is NEVER a correct answer
                match.setScore(0, match.currentQuestionIndex - 1)
                loadNextQuestion()
            }
        }.start()
    }

    fun validateAnswer(view: View){
        if(!isClicked) {
            isClicked = true
            countDownTimer.cancel()
            var isCorrect = false
            when (view.id) {
                ingameBinding.cvAnswerA.id -> {
                    isCorrect = match.checkAnswer(ingameBinding.tvAnswerA.text.toString())
                    if (isCorrect) ingameBinding.layoutAnswerA.setBackgroundResource(R.color.correct_answer) else ingameBinding.layoutAnswerA.setBackgroundResource(R.color.wrong_answer)
                }
                ingameBinding.cvAnswerB.id -> {
                    isCorrect = match.checkAnswer(ingameBinding.tvAnswerB.text.toString())
                    if (isCorrect) ingameBinding.layoutAnswerB.setBackgroundResource(R.color.correct_answer) else ingameBinding.layoutAnswerB.setBackgroundResource(R.color.wrong_answer)
                }
                ingameBinding.cvAnswerC.id -> {
                    isCorrect = match.checkAnswer(ingameBinding.tvAnswerC.text.toString())
                    if (isCorrect) ingameBinding.layoutAnswerC.setBackgroundResource(R.color.correct_answer) else ingameBinding.layoutAnswerC.setBackgroundResource(R.color.wrong_answer)
                }
                ingameBinding.cvAnswerD.id -> {
                    isCorrect = match.checkAnswer(ingameBinding.tvAnswerD.text.toString())
                    if (isCorrect) ingameBinding.layoutAnswerD.setBackgroundResource(R.color.correct_answer) else ingameBinding.layoutAnswerD.setBackgroundResource(R.color.wrong_answer)
                }
            }

            launchSound(isCorrect)
            if (isCorrect) match.setScore(countDown, match.currentQuestionIndex - 1) else match.setScore(0, match.currentQuestionIndex - 1)
            loadNextQuestion()
        }
    }

    fun launchSound(isCorrect: Boolean){
        if(isCorrect)
            audioPlayer = MediaPlayer.create(applicationContext, R.raw.correct_answer)
        else
            audioPlayer = MediaPlayer.create(applicationContext, R.raw.wrong_answer)
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        audioPlayer.start()
    }

    fun loadNextQuestion(){
        object: CountDownTimer(1500, 1000){
            override fun onTick(millisUntilFinished: Long){ }
            override fun onFinish(){ setQuestionOnUI() }            //set up next question on the UI
        }.start()
    }
}