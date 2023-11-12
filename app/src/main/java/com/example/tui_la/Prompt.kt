package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.R

class Prompt: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_prompt)

        val home = findViewById<Button>(R.id.Button_Prompt_tohome)
        home.setOnClickListener {
            val Intent = Intent(this, MainMenu::class.java)
            startActivity(Intent)
        }
    }
}