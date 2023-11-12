package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.R

class Emotions: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_emotions)

        val Happy = findViewById<ImageButton>(R.id.Button_Emotions_happy)
        Happy.setOnClickListener {
            val Intent = Intent(this, MainMenu::class.java)
            startActivity(Intent)
        }
        val Excited = findViewById<ImageButton>(R.id.Button_Emotions_excited)
        Excited.setOnClickListener {
            val Intent = Intent(this, MainMenu::class.java)
            startActivity(Intent)
        }
        val Tired = findViewById<ImageButton>(R.id.Button_Emotions_tired)
        Tired.setOnClickListener {
            val Intent = Intent(this, MainMenu::class.java)
            startActivity(Intent)
        }
        val Afraid = findViewById<ImageButton>(R.id.Button_Emotions_afraid)
        Afraid.setOnClickListener {
            val Intent = Intent(this, Prompt::class.java)
            startActivity(Intent)
        }
        val Stressed = findViewById<ImageButton>(R.id.Button_Emotions_stressed)
        Stressed.setOnClickListener {
            val Intent = Intent(this, Prompt::class.java)
            startActivity(Intent)
        }
        val Sad = findViewById<ImageButton>(R.id.Button_Emotions_sad)
        Sad.setOnClickListener {
            val Intent = Intent(this, Prompt::class.java)
            startActivity(Intent)
        }
        val Relaxed = findViewById<ImageButton>(R.id.Button_Emotions_relaxed)
        Relaxed.setOnClickListener {
            val Intent = Intent(this, MainMenu::class.java)
            startActivity(Intent)
        }
        val Angry = findViewById<ImageButton>(R.id.Button_Emotions_angry)
        Angry.setOnClickListener {
            val Intent = Intent(this, Prompt::class.java)
            startActivity(Intent)
        }
        val Loved = findViewById<ImageButton>(R.id.Button_Emotions_Loved)
        Loved.setOnClickListener {
            val Intent = Intent(this, MainMenu::class.java)
            startActivity(Intent)
        }
        val MM = findViewById<Button>(R.id.Button_Emotions_MM)
        MM.setOnClickListener {
            val Intent = Intent(this, MainMenu::class.java)
            startActivity(Intent)
        }
    }
}