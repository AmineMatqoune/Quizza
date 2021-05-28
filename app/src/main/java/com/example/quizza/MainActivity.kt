package com.example.quizza

import android.content.DialogInterface
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import com.example.quizza.databinding.LauncherBinding

class MainActivity : AppCompatActivity() {
    private lateinit var launcherBinding: LauncherBinding
    private lateinit var countDownTimer: CountDownTimer
    companion object {
        lateinit var audioPlayer: MediaPlayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcherBinding = LauncherBinding.inflate(layoutInflater)
        setContentView(launcherBinding.root)

        launcherBinding.vLayout.setOnClickListener{
            countDownTimer.cancel()
            val intentLogin = Intent(applicationContext, LoginRegister::class.java)
            startActivity(intentLogin)
        }

        launchBackgroundMusic()
        runTimer()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.exit_dialog_title)
        builder.setMessage(R.string.exit_dialog_text)
        builder.setPositiveButton("YES", DialogInterface.OnClickListener{ dialog, it ->
            audioPlayer.stop()
            finish(); })
        builder.setNegativeButton("NO", DialogInterface.OnClickListener{ dialog, it -> })
        builder.create().show()
    }

    private fun launchBackgroundMusic() {
        audioPlayer = MediaPlayer.create(applicationContext, R.raw.background_music)
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        audioPlayer.isLooping = true
        audioPlayer.start()
    }

    private fun runTimer() {
        countDownTimer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                val intentLogin = Intent(applicationContext, LoginRegister::class.java)
                startActivity(intentLogin)
            }
        }.start()
    }
}