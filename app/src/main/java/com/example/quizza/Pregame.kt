package com.example.quizza

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.quizza.databinding.PregameBinding
import com.example.quizza.entities.Match
import com.example.quizza.entities.Question
import com.google.gson.Gson
import kotlinx.android.synthetic.main.pregame.view.*
import kotlinx.coroutines.*

class Pregame: AppCompatActivity() {

    private val MAX_NUMBER_QUESTIONS_FOR_CATEGORY = 2

    private lateinit var pregameBinding: PregameBinding
    private var counter = 4 // 3 seconds + 1 seconds of countdown
    private var filesLoaded = MutableLiveData<Boolean>()
    private val categories = listOf<String>("geography", "math", "music", "physics", "science", "sport")

    private lateinit var singleMatch: Match
    private var categoriesIndex = mutableListOf<Int>()
    private var chosenQuestions = mutableListOf<Int>()
    private val filenames = mutableListOf<String>()
    private val jsonFiles = mutableListOf<String>()
    private var questions = mutableListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pregameBinding = PregameBinding.inflate(layoutInflater)
        setContentView(pregameBinding.root)

        launchCountdownAnimation()
        loadGame()
    }

    override fun onBackPressed() {}

    private fun launchCountdownAnimation(){
        ObjectAnimator.ofFloat(pregameBinding.ivCountDown, "scaleX", 1.6f).apply {
            duration = 1000
            start()
        }
        ObjectAnimator.ofFloat(pregameBinding.ivCountDown, "scaleY", 1.6f).apply {
            duration = 1000
            start()
        }
        runTimer()
    }

    private fun loadGame(){
        setViewModel()

        //generate 4 randoms (different each other -> true) so we know which categories will be loaded this game
        categoriesIndex = Utils.generateRandomNumbers(4, 0, 6, true)
        showCategories()

        //generate 4 randoms (not different each other -> false) so we know which question we'll load for each category
        chosenQuestions = Utils.generateRandomNumbers(4, 1, MAX_NUMBER_QUESTIONS_FOR_CATEGORY + 1, false)

        //build question's filename
        var i = 0
        while(i != 4){
            filenames.add(categories[categoriesIndex[i]] + "_questions/question" + chosenQuestions[i].toString() + ".json")
            i++
        }

        //creiamo una coroutine che si occuperà di accedere ai 4 file nell'asset
        GlobalScope.launch(Dispatchers.IO) {
            jsonFiles.add(async { readFromAssets(filenames[0]) }.await())
            jsonFiles.add(async { readFromAssets(filenames[1]) }.await())
            jsonFiles.add(async { readFromAssets(filenames[2]) }.await())
            jsonFiles.add(async { readFromAssets(filenames[3]) }.await())

            val gson = Gson()
            for(i in 0..3) questions.add(gson.fromJson(jsonFiles[i], Question::class.java))

            filesLoaded.postValue(true)
        }
    }

    private fun runTimer() {
        object: CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter--
                tickSound()
                when(counter){
                    3 -> pregameBinding.ivCountDown.setBackgroundResource(R.drawable.number3)
                    2 -> pregameBinding.ivCountDown.setBackgroundResource(R.drawable.number2)
                    1 -> pregameBinding.ivCountDown.setBackgroundResource(R.drawable.number1)
                    0 -> pregameBinding.ivCountDown.setBackgroundResource(R.drawable.game_start)
                }
            }

            override fun onFinish() {
                val intentIngame = Intent(applicationContext, Ingame::class.java)
                intentIngame.putExtra(intent.extras?.getInt("total_score").toString(), singleMatch)
                intentIngame.putExtras(intent.extras!!)
                startActivity(intentIngame)
                finish()
            }
        }.start()
    }

    private fun tickSound(){
        val audioPlayer = MediaPlayer.create(applicationContext, R.raw.countdown_tick)
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        audioPlayer.isLooping = false
        audioPlayer.start()
    }

    private fun readFromAssets(filename: String): String {
        val bufferReader = application.assets.open(filename).bufferedReader()
        return bufferReader.use { it.readText() }
    }

    fun showCategories(){
        //setting text
        pregameBinding.cvCategoryA.tvCategoryA.text = categories[categoriesIndex[0]]
        pregameBinding.cvCategoryB.tvCategoryB.text = categories[categoriesIndex[1]]
        pregameBinding.cvCategoryC.tvCategoryC.text = categories[categoriesIndex[2]]
        pregameBinding.cvCategoryD.tvCategoryD.text = categories[categoriesIndex[3]]

        //setting images
       var imgs = resources.obtainTypedArray(R.array.category_images)
        pregameBinding.tvCategoryA.setBackgroundResource(imgs.getResourceId(categoriesIndex[0], 0))
        pregameBinding.tvCategoryB.setBackgroundResource(imgs.getResourceId(categoriesIndex[1], 0))
        pregameBinding.tvCategoryC.setBackgroundResource(imgs.getResourceId(categoriesIndex[2], 0))
        pregameBinding.tvCategoryD.setBackgroundResource(imgs.getResourceId(categoriesIndex[3], 0))
    }

    private fun setViewModel(){
        val fileLoaderObserver = Observer<Boolean> {
            if(it == true)
                singleMatch = Match(questions)
        }
        filesLoaded.observe(this, fileLoaderObserver)
    }
}