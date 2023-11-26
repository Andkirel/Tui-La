package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tui_la.ui.theme.TuiLaTheme
import kotlinx.coroutines.delay

enum class lengthOptions(val seconds: Int) {
    oneMinute(60),
    threeMinutes(180),
    fiveMinutes(300),
    tenMinutes(600),
    fifteenMinutes(900)
}
enum class promptOptions(val words: String){
    startPrompt("Tap the button to begin!"),
    inPrompt("Breathe in"),
    outPrompt("Breathe out"),
    completedPrompt("All done. Good job!")
}

class GuidedBreathingActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                CreateHeader()
                Spacer(modifier = Modifier.height(500.dp))
                BreathingAnimationAndCountdownTimer()

            }
        }
    }
}
@Composable
fun BreathingAnimationAndCountdownTimer(
   ){
    var isStarted by remember {
        mutableStateOf(false)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Spacer(modifier = Modifier.height(250.dp))
        Box {
            BreathingAnimation(isStarted = isStarted)
        }
    }
}

@Composable
//@Preview(showBackground = true)
fun CreateHeader() {
    Column(
        modifier = Modifier
            .paint(painterResource(id = R.drawable.main_bg), contentScale = ContentScale.Crop)
            .fillMaxSize()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier, contentAlignment = Alignment.TopStart) {
                Button(
                    onClick = { /*TODO: onClick method for back button*/ }, modifier = Modifier
                        .size(75.dp, 75.dp)
                        .background(Color.Transparent),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_button),
                        contentDescription = "",
                        Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .width(IntrinsicSize.Max)
                    .height(IntrinsicSize.Max)
                    .weight(1f)
            )
            Box(modifier = Modifier, contentAlignment = Alignment.TopEnd) {
                Button(
                    onClick = { /*TODO: onClick method for menu button*/ },
                    modifier = Modifier
                        .size(75.dp, 75.dp)
                        .background(Color.Transparent),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.menu_dots),
                        contentDescription = "Menu dots button",
                        Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun updateTransitionData(isStarted: Boolean){
    val transitionData = updateTransitionData(isStarted)
    Box(
        modifier = Modifier
            .padding(0.dp, 10.dp)
    )
    {
        Text(
            text = promptOptions.startPrompt.words, style = TextStyle(
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle(R.font.philosopher),
                textAlign = TextAlign.Center,
                textMotion = TextMotion.Animated
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
    val transition = updateTransition(targetState = promptOptions.values(),label = "prompt")
   /* val inPrompt = transition.animateValue(label = "prompt") {state ->
        if (isStarted) promptOptions.inPrompt.words else promptOptions.outPrompt.words

    }*/
}

//@Preview(showBackground = true)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BreathingAnimation(isStarted: Boolean) {
    TuiLaTheme {
        //region Declared Variables and variables by remember
        val strokeWidth = 5.dp
        val activeBarColor = Color.Blue
        val inactiveBarColor = Color.LightGray
        val blueColor = colorResource(id = R.color.light_blue_900)
        val headerText = "Tap the button to begin!"
        var isInflated by remember {
            mutableStateOf(false)
        }
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        var value by remember {
            mutableStateOf(1f)
        }
        var totalTime = lengthOptions.oneMinute.seconds
        var currentTime by remember {
            mutableStateOf(totalTime)
        }
        var isTimerRunning by remember {
            mutableStateOf(isStarted)
        }
        //endregion
        LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
            if (currentTime > 0 && isTimerRunning) {
                delay(1000)
                currentTime -= 1
                value = currentTime / totalTime.toFloat()
            }
        }
        val scaleNotInfinite by animateDpAsState(
            targetValue = if (isInflated) 600.dp else 175.dp,
            label = "Inflate Breathing Logo",
            animationSpec = repeatable(
                200, animation = tween<Dp>(2500, 1000, easing = Ease),
                RepeatMode.Reverse
            )
        )
        val textValue by remember {
            mutableStateOf(headerText)
        }
        val transitionTextValue = updateTransition(textValue)

        val transition = updateTransition(targetState = promptOptions.values(),label = "prompt")
        //transition.AnimatedContent(modifier = Modifier.background(Color.Green), content = )
           // when (isStarted) promptOptions.inPrompt.words else promptOptions.outPrompt.words

        Column (verticalArrangement = Arrangement.Center){
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 10.dp)
            )
            {
                Text(
                    "Tap the button to begin!", style = TextStyle(
                        color = blueColor,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle(R.font.philosopher),
                        textAlign = TextAlign.Center,
                        textMotion = TextMotion.Animated
                    ),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
                Text(
                    text = promptOptions.inPrompt.words, style = TextStyle(
                        color = blueColor,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle(R.font.philosopher),
                        textAlign = TextAlign.Center,
                        textMotion = TextMotion.Animated
                    ),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
                Text(
                    text = promptOptions.outPrompt.words, style = TextStyle(
                        color = blueColor,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle(R.font.philosopher),
                        textAlign = TextAlign.Center,
                        textMotion = TextMotion.Animated
                    ),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
                Text(
                    text = promptOptions.completedPrompt.words, style = TextStyle(
                        color = blueColor,
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle(R.font.philosopher),
                        textAlign = TextAlign.Center,
                        textMotion = TextMotion.Animated
                    ),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    (-200).dp,
                    Alignment.CenterVertically
                ), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //region Breathing Logo and Outline
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(400.dp, 400.dp)
                ) {
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
                            isTimerRunning = !isTimerRunning
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
                //endregion
                //region Drawing Timer
                Box(contentAlignment = Alignment.Center) {
                    Box {
                        Canvas(
                            modifier = Modifier
                        ) {
                            val arcRadius = 600f
                            val canvasWidth = size.width
                            val canvasHeight = size.height
                            drawArc(
                                color = inactiveBarColor,
                                startAngle = -90f,
                                sweepAngle = 360f,
                                useCenter = false,
                                size = Size(arcRadius, arcRadius),
                                topLeft = Offset(
                                    (canvasWidth / 2) - (arcRadius / 2),
                                    canvasHeight / 2 - (arcRadius / 2)
                                ),
                                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                            )
                            drawArc(
                                color = activeBarColor,
                                startAngle = -90f,
                                sweepAngle = 360f * value,
                                useCenter = false,
                                size = Size(arcRadius, arcRadius),
                                topLeft = Offset(
                                    (canvasWidth / 2) - (arcRadius / 2),
                                    canvasHeight / 2 - (arcRadius / 2)
                                ),
                                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                            )
                        }
                    }
                }
                //endregion
            }
            Spacer(modifier = Modifier.height(100.dp))
            //region Start/Stop Button and Functionality
            Box (modifier = Modifier.align(Alignment.CenterHorizontally)){
                Button(
                    onClick = {
                        if (currentTime <= 0) {
                            currentTime = totalTime
                            isTimerRunning = true
                            isInflated = true
                        } else {
                            isTimerRunning = !isTimerRunning
                            isInflated = !isInflated

                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isTimerRunning || currentTime <= 0) {
                            Color.LightGray
                        } else {
                            Color.Red
                        }
                    )
                )
                {
                    Text(
                        text = if (isTimerRunning && currentTime >= 0) "Stop"
                        else if (!isTimerRunning && currentTime >= 0) "Start"
                        else "Restart"
                    )
                }
            }
            //endregion
        }
    }
}