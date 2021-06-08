package com.example.quizza

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizza.databinding.PostgameBinding
import com.example.quizza.entities.Match
import kotlinx.android.synthetic.main.postgame.*

class Postgame: AppCompatActivity() {

    private lateinit var postgameBinding: PostgameBinding
    private lateinit var match: Match
    private var bundle: Bundle? = null
    private var gameScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postgameBinding = PostgameBinding.inflate(layoutInflater)
        setContentView(postgameBinding.root)
        match = intent.getSerializableExtra(intent.extras?.getInt("total_score").toString()) as Match

        bundle = intent.extras

        postgameBinding.btnNext.setOnClickListener{
            val intentHomepage = Intent(applicationContext, Homepage::class.java)
            //updateDB
            val totalScore = bundle!!.getInt("total_score") + gameScore
            bundle!!.putInt("total_score", totalScore)
            intentHomepage.putExtras(bundle!!)
            startActivity(intentHomepage)
            finish()
        }

        launchCheeringSound()
        setupUI()
    }

    override fun onBackPressed() {}

    private fun setupUI(){
        //setting background categories
        when(match.getQuestions()[0].getCategory()){
            "0" ->{ postgameBinding.lCategoryA.setBackgroundResource(R.drawable.geography_bg)
                    postgameBinding.tvCategoryAPG.text = getString(R.string.geo_label) }
            "1" ->{ postgameBinding.lCategoryA.setBackgroundResource(R.drawable.math_bg)
                postgameBinding.tvCategoryAPG.text = getString(R.string.math_label) }
            "2" ->{ postgameBinding.lCategoryA.setBackgroundResource(R.drawable.music_bg)
                postgameBinding.tvCategoryAPG.text = getString(R.string.music_label) }
            "3" ->{ postgameBinding.lCategoryA.setBackgroundResource(R.drawable.physics_bg)
                postgameBinding.tvCategoryAPG.text = getString(R.string.physics_label) }
            "4" ->{ postgameBinding.lCategoryA.setBackgroundResource(R.drawable.science_bg)
                postgameBinding.tvCategoryAPG.text = getString(R.string.science_label) }
            "5" ->{ postgameBinding.lCategoryA.setBackgroundResource(R.drawable.sport_bg)
                postgameBinding.tvCategoryAPG.text = getString(R.string.sport_label) }
        }
        when(match.getQuestions()[1].getCategory()){
            "0" ->{ postgameBinding.lCategoryB.setBackgroundResource(R.drawable.geography_bg)
                postgameBinding.tvCategoryBPG.text = getString(R.string.geo_label) }
            "1" ->{ postgameBinding.lCategoryB.setBackgroundResource(R.drawable.math_bg)
                postgameBinding.tvCategoryBPG.text = getString(R.string.math_label) }
            "2" ->{ postgameBinding.lCategoryB.setBackgroundResource(R.drawable.music_bg)
                postgameBinding.tvCategoryBPG.text = getString(R.string.music_label) }
            "3" ->{ postgameBinding.lCategoryB.setBackgroundResource(R.drawable.physics_bg)
                postgameBinding.tvCategoryBPG.text = getString(R.string.physics_label) }
            "4" ->{ postgameBinding.lCategoryB.setBackgroundResource(R.drawable.science_bg)
                postgameBinding.tvCategoryBPG.text = getString(R.string.science_label) }
            "5" ->{ postgameBinding.lCategoryB.setBackgroundResource(R.drawable.sport_bg)
                postgameBinding.tvCategoryBPG.text = getString(R.string.sport_label) }
        }
        when(match.getQuestions()[2].getCategory()){
            "0" ->{ postgameBinding.lCategoryC.setBackgroundResource(R.drawable.geography_bg)
                postgameBinding.tvCategoryCPG.text = getString(R.string.geo_label) }
            "1" ->{ postgameBinding.lCategoryC.setBackgroundResource(R.drawable.math_bg)
                postgameBinding.tvCategoryCPG.text = getString(R.string.math_label) }
            "2" ->{ postgameBinding.lCategoryC.setBackgroundResource(R.drawable.music_bg)
                postgameBinding.tvCategoryCPG.text = getString(R.string.music_label) }
            "3" ->{ postgameBinding.lCategoryC.setBackgroundResource(R.drawable.physics_bg)
                postgameBinding.tvCategoryCPG.text = getString(R.string.physics_label) }
            "4" ->{ postgameBinding.lCategoryC.setBackgroundResource(R.drawable.science_bg)
                postgameBinding.tvCategoryCPG.text = getString(R.string.science_label) }
            "5" ->{ postgameBinding.lCategoryC.setBackgroundResource(R.drawable.sport_bg)
                postgameBinding.tvCategoryCPG.text = getString(R.string.sport_label) }
        }
        when(match.getQuestions()[3].getCategory()){
            "0" ->{ postgameBinding.lCategoryD.setBackgroundResource(R.drawable.geography_bg)
                postgameBinding.tvCategoryDPG.text = getString(R.string.geo_label) }
            "1" ->{ postgameBinding.lCategoryD.setBackgroundResource(R.drawable.math_bg)
                postgameBinding.tvCategoryDPG.text = getString(R.string.math_label) }
            "2" ->{ postgameBinding.lCategoryD.setBackgroundResource(R.drawable.music_bg)
                postgameBinding.tvCategoryDPG.text = getString(R.string.music_label) }
            "3" ->{ postgameBinding.lCategoryD.setBackgroundResource(R.drawable.physics_bg)
                postgameBinding.tvCategoryDPG.text = getString(R.string.physics_label) }
            "4" ->{ postgameBinding.lCategoryD.setBackgroundResource(R.drawable.science_bg)
                postgameBinding.tvCategoryDPG.text = getString(R.string.science_label) }
            "5" ->{ postgameBinding.lCategoryD.setBackgroundResource(R.drawable.sport_bg)
                postgameBinding.tvCategoryDPG.text = getString(R.string.sport_label) }
        }

        //setting images
        if(match.getScore(0) == 0) postgameBinding.ivQuestionA.setBackgroundResource(R.drawable.wrong_answer) else postgameBinding.ivQuestionA.setBackgroundResource(R.drawable.right_answer)
        if(match.getScore(1) == 0) postgameBinding.ivQuestionB.setBackgroundResource(R.drawable.wrong_answer) else postgameBinding.ivQuestionB.setBackgroundResource(R.drawable.right_answer)
        if(match.getScore(2) == 0) postgameBinding.ivQuestionC.setBackgroundResource(R.drawable.wrong_answer) else postgameBinding.ivQuestionC.setBackgroundResource(R.drawable.right_answer)
        if(match.getScore(3) == 0) postgameBinding.ivQuestionD.setBackgroundResource(R.drawable.wrong_answer) else postgameBinding.ivQuestionD.setBackgroundResource(R.drawable.right_answer)

        //setting textviews
        postgameBinding.tvQuestionA.text = "+" + match.getScore(0).toString() + " pts"
        postgameBinding.tvQuestionB.text = "+" + match.getScore(1).toString() + " pts"
        postgameBinding.tvQuestionC.text = "+" + match.getScore(2).toString() + " pts"
        postgameBinding.tvQuestionD.text = "+" + match.getScore(3).toString() + " pts"

        gameScore = match.getScore(0) + match.getScore(1) + match.getScore(2) + match.getScore(3)
        postgameBinding.tvGameScore.text = "+ " + gameScore.toString() + " pts"
        val totalScore = bundle?.getInt("total_score")!!
        postgameBinding.tvTotalScoreUpdated.text = (totalScore + gameScore).toString() + " pts"
    }

    fun launchCheeringSound(){
        val audioPlayer = MediaPlayer.create(applicationContext, R.raw.people_cheering)
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        audioPlayer.start()
    }
}