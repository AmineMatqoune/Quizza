package com.example.quizza

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizza.databinding.PostgameBinding
import com.example.quizza.entities.Match

class Postgame: AppCompatActivity() {

    private lateinit var postgameBinding: PostgameBinding
    private lateinit var match: Match

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postgameBinding = PostgameBinding.inflate(layoutInflater)
        setContentView(postgameBinding.root)
        match = intent.getSerializableExtra("match_instance") as Match

    }
}