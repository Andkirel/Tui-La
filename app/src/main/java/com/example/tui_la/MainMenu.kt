package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.R

class MainMenu: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mainscreen)

        val GMeditation = findViewById<Button>(R.id.Button_MainScreen_GM)
        GMeditation .setOnClickListener {
            val Intent = Intent(this, GuidedMeditationActivity::class.java)
            startActivity(Intent)
        }
        /*val Edu = findViewById<Button>(R.id.Button_MainScreen_EDU)
        Edu.setOnClickListener {
            val Intent = Intent(this, Education::class.java)
            startActivity(Intent)
        }*/
        val Crisis = findViewById<Button>(R.id.Button_MainScreen_Crisis)
        Crisis.setOnClickListener {
            val Intent = Intent(this, Csupport::class.java)
            startActivity(Intent)
        }
        val MMSettings = findViewById<ImageButton>(R.id.ImageButton_MainMenu_Settings)
        MMSettings.setOnClickListener {
            val Intent = Intent(this, Settings_menu::class.java)
            startActivity(Intent)
        }
    }
}