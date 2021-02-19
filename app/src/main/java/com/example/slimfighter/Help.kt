package com.example.slimfighter

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.slimfighter.MyVariables.playMusic

class Help : AppCompatActivity() {
    var player: MediaPlayer? = null
    var pref: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_help)

        pref = getSharedPreferences("my_shared_preferences", MODE_PRIVATE)
        playMusic = pref?.getBoolean("playMusic",true)!!

        val settings: ImageView = findViewById(R.id.settings)
        val closeHelp: ImageView = findViewById(R.id.closeHelp)
        closeHelp.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fun displayMusicManagement(){
            if (playMusic){
                settings.setImageResource(R.drawable.playtrue)
            } else {
                settings.setImageResource(R.drawable.playfalse)
            }
        }

        displayMusicManagement()

        settings.setOnClickListener {
            playMusic=!playMusic
           displayMusicManagement()
            if (!playMusic){
                player?.stop()
            } else {
                player = MediaPlayer.create(this, R.raw.main)
                player?.isLooping = true
                player?.start()
            }
        }

        player = MediaPlayer.create(this, R.raw.main)
        player?.isLooping = true
        if (MyVariables.playMusic){
            player?.start()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        val editor = pref?.edit()
        editor?.putBoolean("playMusic",playMusic)

        editor?.apply()
    }

}