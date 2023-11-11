package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tui_la.ui.theme.TuiLaTheme

class GuidedBreathingActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TuiLaTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
                GreetingPreview()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TuiLaTheme {
        var isInflated by remember {
            mutableStateOf(false)
        }
        var startPrompt by remember {
            mutableStateOf("Tap the logo to begin")
        }
        var prompts by remember {
            mutableStateOf(listOf<String>("Breathe In","Breathe Out"))
        }
        var time by remember {
            mutableStateOf(5)
        }
        val logoSize by animateDpAsState(
            targetValue = if (isInflated) 300.dp else 200.dp,
            label = "Inflate Logo Size onClick"
        )
        Column {
            Text(text = startPrompt)
            Row (horizontalArrangement = Arrangement.spacedBy((-175).dp),
                verticalAlignment = Alignment.CenterVertically
                ){

                Box {
                    Image(
                        painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
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
                }
                    Button(
                        onClick = {
                            /*if (time != 0) {
                        time--
                        startPrompt = "Breathe In"
                    }*/
                            isInflated = !isInflated
                        },
                        modifier = Modifier
                            .background(Color.Transparent)
                            .size(logoSize),
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                    ) {
                        AnimatedVisibility(visible = isInflated, enter = scaleIn()+ expandVertically(expandFrom = Alignment.CenterVertically), exit = scaleOut()+ shrinkVertically(shrinkTowards = Alignment.CenterVertically)) {
                            Image(
                                painterResource(id = R.drawable.logo),
                                contentDescription = "",
                                modifier = Modifier
                                    .background(Color.Transparent),
                                Alignment.Center,
                                ContentScale.Inside
                            )
                        }

                    }

            }

            /*Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(logoSize)
            ) {
                Button(
                    onClick = {
                        *//*if (time != 0) {
                        time--
                        startPrompt = "Breathe In"
                    }*//*
                        isInflated = !isInflated
                    },
                    modifier = Modifier
                        .background(Color.Transparent),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                ) {
                    Image(
                        painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier
                            .background(Color.Transparent),
                        Alignment.Center,
                        ContentScale.Inside
                    )

                }*/

                /*AnimatedVisibility(
                visible = true,
                enter = scaleIn(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    initialScale = 20f,
                    transformOrigin = TransformOrigin.Center
                ) + expandVertically(expandFrom = Alignment.CenterVertically),
                modifier = Modifier.size(200.dp)
                ) {
                startPrompt = "Yes"
            }*/

        }
    }
}


