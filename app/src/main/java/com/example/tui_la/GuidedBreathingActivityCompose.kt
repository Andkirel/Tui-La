package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tui_la.ui.theme.TuiLaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds

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

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TuiLaTheme {
        var isInflated by remember {
            mutableStateOf(false)
        }
        val buttonInteractionSource = remember { MutableInteractionSource()}
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

        val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
        val scaleItem by infiniteTransition.animateFloat(
            initialValue = 0.75f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(tween(3000, delayMillis= 1500), repeatMode = RepeatMode.Reverse),
            label = "scale"
        )
        val scaleNotInfinite by animateDpAsState(
            targetValue = if (isInflated) 600.dp else 175.dp,
            animationSpec = repeatable(200, animation = tween<Dp>(2500,1000, easing = Ease),
                RepeatMode.Reverse),
            label = ""
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
                            .size(scaleNotInfinite),
                    )
                }
            }
        }
    }
}

fun Modifier.onTouchHeld(
    pollDelay: Duration = 3000.milliseconds,
    onTouchHeld: (timeElapsed: Duration) -> Unit
) = composed {
    val scope = rememberCoroutineScope()
    pointerInput(onTouchHeld) {
        awaitEachGesture {
            val initialDown = awaitFirstDown(requireUnconsumed = false)
            val initialDownTime = System.nanoTime()
            val initialTouchHeldJob = scope.launch {
                while (initialDown.pressed) {
                    val timeElapsed = System.nanoTime() - initialDownTime
                    onTouchHeld(timeElapsed.nanoseconds)
                    delay(pollDelay)
                }
            }
            //waitForUpOrCancellation()
            initialTouchHeldJob.cancel()
        }
    }
}
fun Modifier.scaleOnPress(
    interactionSource: InteractionSource
) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        if (isPressed) {
            0.95f
        } else {
            1f
        }
    )
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
}