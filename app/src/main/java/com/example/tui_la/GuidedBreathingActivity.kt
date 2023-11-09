package com.example.tui_la

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.TransformOrigin


class GuidedBreathingActivity: AppCompatActivity() {
    private var navigationBackButton = findViewById<ImageButton>(R.id.breathing_back_button)
    private var inflatableLogo = findViewById<ImageButton>(R.id.breathing_inflatable_logo)
    private var startStopButton = findViewById<Button>(R.id.button_breathing_start_stop)
    private var timerNumbers = findViewById<TextClock>(R.id.breathing_timer_clock)
    private var timerCircle = findViewById<ProgressBar>(R.id.breathing_timer_progress)
    private var promptText = findViewById<TextView>(R.id.breathing_prompt)
    private var circleOutline = findViewById<ImageView>(R.id.breathing_circle_outline)
    

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_guided_breathing_exercise)

        startStopButton.setOnClickListener {

        }

    }
    @Composable
    fun startBreathingExercise() {
        circleOutline.visibility = View.VISIBLE
        /*inflatableLogo(
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            initialScale = inflatableLogo.scaleX,
            transformOrigin = TransformOrigin.Center
        ) + expandVertically(expandFrom = Alignment.CenterVertically)*/
        scaleIn(
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            initialScale = inflatableLogo.scaleX,
            transformOrigin = TransformOrigin.Center
        ) + expandVertically(expandFrom = Alignment.CenterVertically)


    }

}