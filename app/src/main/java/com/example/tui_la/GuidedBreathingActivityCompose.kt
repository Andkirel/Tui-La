package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
            mutableStateOf(listOf<String>("Breathe In", "Breathe Out"))
        }
        val sizeTransition = updateTransition(targetState = isInflated, label = null)
        val logoSizeOffset by sizeTransition.animateOffset(
            transitionSpec = { tween(1000) },
            label = "offSetLeft",
            targetValueByState = { isInflated ->
                if (isInflated) Offset(0f, 100f) else Offset.Zero
            }
        )
        val logoSize by animateDpAsState(
            targetValue = if (isInflated) 300.dp else 200.dp,
            label = "Inflate Logo Size onClick"
        )
        Column {
            Text(text = startPrompt)
            /*Row(
                horizontalArrangement = Arrangement.spacedBy((-175).dp),
                verticalAlignment = Alignment.CenterVertically
            ) */
            /*Box(contentAlignment = Alignment.Center, modifier = Modifier.clipToBounds()){
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
                )
                Button(
                    onClick = {
                        isInflated = !isInflated
                    },
                    modifier = Modifier
                        .background(Color.Transparent)
                        .align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = CircleShape
                ) {
                    AnimatedContent(
                        targetState = isInflated,
                        label = "tryMe",
                        content = { isInflated ->
                            if (isInflated) {
                                Image(
                                    painterResource(id = R.drawable.logo),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .size(300.dp),
                                        //.absoluteOffset((-75).dp, 0.dp),
                                )
                            } else {
                                Image(
                                    painterResource(id = R.drawable.logo),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .size(150.dp)
                                        .absoluteOffset(0.dp, 0.dp),
                                    Alignment.Center
                                )
                            }
                        }
                    )
                }
            }*/
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .border(
                        BorderStroke(
                            width = 4.dp,
                            colorResource(id = R.color.light_coral), //TODO: Change color to cactus_purple when ready to finish
                        ), shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { isInflated = !isInflated },
                    modifier = Modifier
                        .background(Color.Transparent),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    AnimatedContent(
                        targetState = isInflated,
                        label = "",
                        transitionSpec = {
                                scaleIn(animationSpec = tween(1000)) + expandIn(
                                    expandFrom = Alignment.Center) togetherWith 
                                scaleOut(animationSpec = tween(1000)) + shrinkOut(
                                    shrinkTowards = Alignment.Center
                                )
                        },
                        content = { isInflated ->
                            if (isInflated) {
                                Image(
                                    painterResource(id = R.drawable.logo),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .size(300.dp),

                                )
                            } else {
                                Image(
                                    painterResource(id = R.drawable.logo),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .size(200.dp),
                                )
                            }
                        },
                        )
                }
            }
        }
    }
}


