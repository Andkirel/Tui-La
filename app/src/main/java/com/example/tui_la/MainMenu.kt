package com.example.tui_la

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView

class MainMenu: AppCompatActivity() {
    private lateinit var mainAdManagerAdView: AdManagerAdView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mainscreen)

        MobileAds.initialize(this)
        mainAdManagerAdView = findViewById(R.id.mainLayAdManagerAdView)
        val adRequest = AdManagerAdRequest.Builder().build()
        mainAdManagerAdView.loadAd(adRequest)

        val GMeditation = findViewById<Button>(R.id.Button_MainScreen_GM)
        GMeditation.setOnClickListener {
            val Intent = Intent(this, GuidedMeditationCompose::class.java)
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