package com.example.quizza

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quizza.databinding.LoginRegisterBinding

class LoginRegister : AppCompatActivity(){

    private lateinit var loginBinding: LoginRegisterBinding
    private lateinit var registerDialog: RegisterDialog
    private lateinit var loginDialog: LoginDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = LoginRegisterBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.buttonLogin.setOnClickListener{
            loginDialog = LoginDialog(this)
            loginDialog.show(supportFragmentManager, "loginDialog")
        }

        loginBinding.buttonRegister.setOnClickListener{
            registerDialog = RegisterDialog(this)
            registerDialog.show(supportFragmentManager, "registerDialog")
        }

        loginBinding.btnMusic.setOnClickListener{
            val builder = AlertDialog.Builder(this)

            builder.setTitle(R.string.sound_alert_title)
            builder.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, it -> })

            if(MainActivity.audioPlayer.isPlaying) {        //sound is playing
                builder.setMessage(R.string.sound_disable_text)
                builder.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, it -> MainActivity.audioPlayer.pause()})
            }else{                                       //sound is not playing
                builder.setMessage(R.string.sound_enable_text)
                builder.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, it -> MainActivity.audioPlayer.start()})
            }

            builder.create().show()
        }

        loginBinding.btnPalette.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.choose_theme))
            builder.setItems(arrayOf("Dark", "Light", "Neutral"), DialogInterface.OnClickListener{ dialog, which -> })
            builder.create().show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish();
    }

}