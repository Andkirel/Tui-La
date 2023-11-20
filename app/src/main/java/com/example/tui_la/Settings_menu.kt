package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.media3.common.util.UnstableApi
import com.example.tui_la.R

@UnstableApi class Settings_menu: AppCompatActivity() {

    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_settings)

        uid = intent.getStringExtra("UID") ?: return

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            backToHome()
        }
    }
    private fun backToHome() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.putExtra("UID", uid) // Adding the UID to the intent
        startActivity(homeIntent)
    }
}