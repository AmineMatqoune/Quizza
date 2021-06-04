package com.example.quizza

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.quizza.databinding.PregameBinding
import com.example.quizza.entities.Match
import kotlinx.android.synthetic.main.pregame.view.*
import kotlinx.coroutines.*
import kotlin.random.Random

class Pregame: AppCompatActivity() {

    private val MAX_NUMBER_QUESTIONS_FOR_CATEGORY = 2

    private lateinit var pregameBinding: PregameBinding
    private var counter = 4 // 3 seconds + 1 seconds of countdown
    private var filesLoaded = MutableLiveData<Boolean>()
    private val categories = listOf<String>("geography", "math", "music", "physics", "science", "sport")
    private val categoriesImage = listOf<Int>(R.drawable.geography_bg, R.drawable.math_bg, R.drawable.music_bg,
            R.drawable.physics_bg, R.drawable.science_bg, R.drawable.sport_bg)

    //attributi spostati da Match e Question
    private var categoriesIndex = mutableListOf<Int>()
    private var chosenQuestions = mutableListOf<Int>()
    private val filenames = mutableListOf<String>()
    private val jsonFiles = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pregameBinding = PregameBinding.inflate(layoutInflater)
        setContentView(pregameBinding.root)

        //fai animazione countdown
        launchCountdownAnimation()

        //Fai apparire sotto le categorie che usciranno
        loadGame()
    }

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
        //fai l'accesso al file e crea i quattro oggetti question
        //avvia Ingame.kt

        setViewModel()

        //generate 4 randoms (different each other -> true) so we know which categories will be loaded this game
        categoriesIndex = generateRandomNumbers(4, 0, 6, true)
        showCategories()

        //generate 4 randoms (not different each other -> false) so we know which question we'll load for each category
        chosenQuestions = generateRandomNumbers(4, 1, MAX_NUMBER_QUESTIONS_FOR_CATEGORY + 1, false)

        //build question's filename
        var i = 0
        while(i != 4){
            filenames.add(categories[categoriesIndex[i]] + "_questions/question" + chosenQuestions[i].toString() + ".json")
            i++
        }

        //creiamo una coroutine che si occuperà di accedere ai 4 file nell'asset
        GlobalScope.launch(Dispatchers.IO) {
            jsonFiles.add(async { readFromAssets(filenames[0]) }.toString())
            jsonFiles.add(async { readFromAssets(filenames[1]) }.toString())
            jsonFiles.add(async { readFromAssets(filenames[2]) }.toString())
            jsonFiles.add(async { readFromAssets(filenames[3]) }.toString())
            filesLoaded.postValue(true)
        }


        //il chiamante attende che le coroutine carichino in jsonFiles i 4 file e li passa a match
        //val match: Match(jsonFiles.await())

        //SONO ARRIVATO QUI..DA COMPLETARE IL PASSAGGIO DI JSONFILES A MATCH COSICCHE' POSSA CREARE TRAMITE GSON 4 QUESTION
}

    private fun runTimer() {
        object: CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter--
                tickSound()
                /*when(counter.value){
                    3 -> pregameBinding.ivCountDown.setBackgroundResource(R.drawable.number3)
                    2 -> pregameBinding.ivCountDown.setBackgroundResource(R.drawable.number2)
                    1 -> pregameBinding.ivCountDown.setBackgroundResource(R.drawable.number1)
                    0 -> pregameBinding.ivCountDown.setBackgroundResource(R.drawable.game_start)
                }*/
            }

            override fun onFinish() {
                println("Mo parte Ingame.kt")
//                val intentIngame = Intent(applicationContext, Ingame::class.java)
//                startActivity(intentIngame)
            }
        }.start()
    }

    private fun tickSound(){
        val audioPlayer = MediaPlayer.create(applicationContext, R.raw.countdown_tick)
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        audioPlayer.isLooping = false
        audioPlayer.start()
    }

    private fun generateRandomNumbers(numOfRandomNumbers: Int, lowerBound: Int, upperBound: Int, differentNumbers: Boolean): MutableList<Int>{
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
        }else{       //generate random numbers
            while(i < numOfRandomNumbers){
                randomNumbers.add(Random.nextInt(lowerBound , upperBound))
                i++
            }
        }

        return randomNumbers
    }

    private fun readFromAssets(filename: String): String {
        val bufferReader = application.assets.open(filename).bufferedReader()
        val data = bufferReader.use { it.readText() }
        return data
    }

    fun showCategories(){
        //setting text
        pregameBinding.cvCategoryA.tvCategoryA.text = categories[categoriesIndex[0]]
        pregameBinding.cvCategoryB.tvCategoryB.text = categories[categoriesIndex[1]]
        pregameBinding.cvCategoryC.tvCategoryC.text = categories[categoriesIndex[2]]
        pregameBinding.cvCategoryD.tvCategoryD.text = categories[categoriesIndex[3]]

        //settingImages
       var images = resources.obtainTypedArray(R.array.category_images)
        pregameBinding.cvCategoryB.background = images.getResourceId(1, 0)
        pregameBinding.cvCategoryC.background = categoriesImage[categoriesIndex[2]].toDrawable()
        pregameBinding.cvCategoryD.background = categoriesImage[categoriesIndex[3]].toDrawable()
    }

    private fun setViewModel(){
        val fileLoaderObserver = Observer<Boolean> {
            if(it == true) Toast.makeText(applicationContext, "File caricati", Toast.LENGTH_LONG).show()
        }
        filesLoaded.observe(this, fileLoaderObserver)
    }
}