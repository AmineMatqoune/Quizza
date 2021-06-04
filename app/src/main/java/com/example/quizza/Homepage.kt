package com.example.quizza

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizza.databinding.HomepageBinding

class Homepage: AppCompatActivity() {

    private lateinit var homepageBinding: HomepageBinding
    private var myBundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homepageBinding = HomepageBinding.inflate(layoutInflater)
        setContentView(homepageBinding.root)

        myBundle = intent.extras
        when(myBundle?.getString("avatar")){    //myBundle can be null-able
            "Joe" -> homepageBinding.ivAvatarHomepage.setBackgroundResource(R.drawable.joe)
            "Boe" -> homepageBinding.ivAvatarHomepage.setBackgroundResource(R.drawable.boe)
            "Lara" -> homepageBinding.ivAvatarHomepage.setBackgroundResource(R.drawable.lara)
            "Meg" -> homepageBinding.ivAvatarHomepage.setBackgroundResource(R.drawable.meg)
            "Mike" -> homepageBinding.ivAvatarHomepage.setBackgroundResource(R.drawable.mike)
            "Rosa" -> homepageBinding.ivAvatarHomepage.setBackgroundResource(R.drawable.rosa)
        }

        homepageBinding.tvUsernameHomepage.text = myBundle?.getString("username")
        homepageBinding.tvScoreHomepage.text = "+" + myBundle?.getInt("totalScore").toString() + " pts"
        homepageBinding.btnPlay.setOnClickListener{
            val intentPregame = Intent(applicationContext, Pregame::class.java)
            startActivity(intentPregame)
        }
    }

    override fun onBackPressed() {
        //Fai l'alert dialog, lo stesso nel main activity in onBackPressed()
        println("Pressed")
    }
}