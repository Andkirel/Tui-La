package com.example.tui_la

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuidedBreathingActivity: AppCompatActivity() {
    private var navigationBackButton = findViewById<ImageButton>(R.id.breathing_back_button)
    private var inflatableLogo = findViewById<ImageButton>(R.id.breathing_inflatable_logo)
    private var startStopButton = findViewById<Button>(R.id.button_breathing_start_stop)
    private var timerNumbers = findViewById<TextClock>(R.id.breathing_timer_clock)
    private var timerCircle = findViewById<ProgressBar>(R.id.breathing_timer_progress)
    private var promptText = findViewById<TextView>(R.id.breathing_prompt)

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_guided_breathing_exercise)


    }
}