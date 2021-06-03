package com.example.quizza

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.quizza.databinding.PregameBinding
import com.example.quizza.entities.Match
import kotlinx.android.synthetic.main.pregame.*
import kotlinx.coroutines.*
import kotlin.random.Random

class Pregame: AppCompatActivity() {

    private lateinit var pregameBinding: PregameBinding
    private var counter = MutableLiveData<Int>()

    //attributi spostati da Match e Question
    private var categoriesIndex = mutableListOf<Int>()
    private val categories = listOf<String>("geography", "math", "music", "physics", "science", "sport")
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
        counter.value = 4
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

        //generiamo i 4 numeri casuali a cui corrisponderanno le categorie delle domande
        categoriesIndex = generateRandomNumbers(4, 6)

        //generiamo 4 numeri per identificare le domande da prendere per categoria
        val temp = generateRandomNumbers(4,2)

        //per ogni domanda dobbiamo generarci il file casuale relativo alla domanda
        var i : Int = 0
        while(i != 4){
            filenames[i] = categories[categoriesIndex[i]] + "_questions/question" + temp[i].toString() + ".json"
            i++
        }

        //creiamo 4 coroutine che si occuperanno di accedere ai file nell'asset
        GlobalScope.launch(Dispatchers.IO) {
            jsonFiles[0] = async { readFromAssets(filenames[0]) }.toString()
            jsonFiles[1] = async { readFromAssets(filenames[1]) }.toString()
            jsonFiles[2] = async { readFromAssets(filenames[2]) }.toString()
            jsonFiles[3] = async { readFromAssets(filenames[3]) }.toString()
        }


        //il chiamante attende che le coroutine carichino in jsonFiles i 4 file e li passa a match
        val match: Match(jsonFiles.await())

        //SONO ARRIVATO QUI..DA COMPLETARE IL PASSAGGIO DI JSONFILES A MATCH COSICCHE' POSSA CREARE TRAMITE GSON 4 QUESTION
}

    private fun runTimer() {
        object: CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter.value = counter.value?.minus(1)
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

    private fun readFromAssets(filename: String): String {
        val bufferReader = application.assets.open(filename).bufferedReader()
        val data = bufferReader.use { it.readText() }
        return data
    }
}