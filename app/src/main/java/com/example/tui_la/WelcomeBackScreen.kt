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

        /*TODO: add code to determine if new or returning user. Depending on answer, adjust WelcomeBackText to say "Welcome".
           Also adjust the usernameText to the correct username.*/
        /* Tentative code?
        private val userRef = db.collection("user").document("userID")
        userRef.get()
          .addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    */

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