package com.example.tui_la

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Csupport: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_support)

        val Crisis = findViewById<ImageButton>(R.id.Backbutton_Crisis)
        Crisis.setOnClickListener {
            val Intent = Intent(this, MainMenu::class.java)
            startActivity(Intent)
        }
    }
}