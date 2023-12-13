package com.example.tui_la

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Prompt: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_prompt)

        val home = findViewById<Button>(R.id.Button_Prompt_tohome)
        home.setOnClickListener {
            //TODO: Direct user to HomeActivity
        }
        //TODO: Create onClickListeners to direct user to Journal
    }
}