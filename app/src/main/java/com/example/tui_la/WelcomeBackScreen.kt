package com.example.tui_la

import android.content.Intent
import android.os.Bundle

import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class WelcomeBackScreen : AppCompatActivity() {
    private var progressBarStatus = 0
    private var loadInt:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_welcome_welcomeback)

        val progressBar = findViewById<ProgressBar>(R.id.welcomeScreenProgressBar)
        Thread(Runnable {
            while (progressBarStatus < 100){
                try{
                    loadInt += 25
                    Thread.sleep(1000)
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
                this.progressBarStatus = loadInt
                progressBar.progress = progressBarStatus
            }
        }).start()
        launchHowFeelingScreen()
    }
    private fun launchHowFeelingScreen() {
        val intent = Intent(this, FirstFragment::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.finish()
    }

}