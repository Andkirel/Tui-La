package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
        val sizeTransition = updateTransition(
            targetState = isInflated,
            label = null)
        val logoSize by sizeTransition.animateDp(
            transitionSpec = { tween(3000) },
            label = "Inflate logo size",
            targetValueByState = { isInflated ->
                if (isInflated) 600.dp else 175.dp
            }
        )

        Column {
            Text(text = startPrompt)
            Box(contentAlignment = Alignment.Center,modifier = Modifier
                .size(400.dp,400.dp)
            ){
                Image(
                    painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(165.dp, 165.dp)
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
                ) {
                    Image(
                        painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier
                            .background(Color.Transparent)
                            .size(logoSize),
                    )
                }
            }
        }
    }
}


