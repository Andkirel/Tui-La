package com.example.tui_la

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class GuidedBreathingActivity: AppCompatActivity() {
    //private var navigationBackButton = findViewById<ImageButton>(R.id.breathing_back_button)
//    private var inflatableLogo = findViewById<ImageButton>(R.id.breathing_inflatable_logo)
    //private var startStopButton = findViewById<Button>(R.id.button_breathing_start_stop)
    //private var timerNumbers = findViewById<TextClock>(R.id.breathing_timer_clock)
    //private var timerCircle = findViewById<ProgressBar>(R.id.breathing_timer_progress)
    //private var promptText = findViewById<TextView>(R.id.breathing_prompt)
  //  private var circleOutline = findViewById<ImageView>(R.id.breathing_circle_outline)
  /*

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_guided_breathing_exercise)

        startStopButton.setOnClickListener {

        }

    }*/
   /* @Preview
    @Composable
    fun startBreathingExercise() {
        circleOutline.visibility = View.VISIBLE
        *//*inflatableLogo(
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            initialScale = inflatableLogo.scaleX,
            transformOrigin = TransformOrigin.Center
        ) + expandVertically(expandFrom = Alignment.CenterVertically)*//*
        scaleIn(
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            initialScale = inflatableLogo.scaleX,
            transformOrigin = TransformOrigin.Center
        ) + expandVertically(expandFrom = Alignment.CenterVertically)*/
        /*CircleShape(
            Image(
                painterResource(id = R.drawable.logo),
                modifier = Modifier
                    .size(100.dp, 175.dp)
                    .background(Color.Transparent)
                    .border(
                        border = BorderStroke(
                            width = 5.dp,
                            color = colorResource(id = R.color.cactus_purple)
                        ), shape = CircleShape
                    )
            )
        )*/

    }
    @Preview(showBackground = true)
    @Composable
    fun inflateLogo(){
        Box(modifier = Modifier
            .size(300.dp,300.dp),
            ) {
            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(150.dp, 150.dp)
                    .background(Color.Transparent)
                    .border(
                        BorderStroke(
                            width = 4.dp,
                            colorResource(id = R.color.light_coral), //TODO: Change color to cactus_purple when ready to finish
                        ), shape = CircleShape
                    ),
                Alignment.Center,
                contentScale = ContentScale.Inside
            )
            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(150.dp, 150.dp)
                    .background(Color.Transparent),
                Alignment.Center,
                contentScale = ContentScale.Inside
            )
        }
    }