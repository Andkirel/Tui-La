package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.interaction.MutableInteractionSource
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

class GuidedBreathingActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                //var start by remember { mutableStateOf(false)}
                //val onClick = { start = !start }

                CreateButtonAndTitleLayout()
                Spacer(modifier = Modifier.height(250.dp))
                Spacer(modifier = Modifier.height(250.dp))
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
    val interactionSource = remember { MutableInteractionSource()}
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        /*verticalArrangement = Arrangement.spacedBy((-200).dp,Alignment.Top),*/
        ) {
        Spacer(modifier = Modifier.height(250.dp))
        Box {
            BreathingAnimation(isStarted = isStarted)
        }
        /*Box{
            BreathingTime(
                totalTime = lengthOptions.fiveMinutes.seconds,
                inactiveBarColor = Color.LightGray,
                activeBarColor = Color.Blue,
                modifier = Modifier
                    .size(300.dp),
                isStarted = isStarted
            )
        }*/
    }
}

@Composable
//@Preview(showBackground = true)
fun CreateButtonAndTitleLayout() {
    val blueColor = colorResource(id = R.color.light_blue_900)
    Column(
        modifier = Modifier
            .paint(painterResource(id = R.drawable.main_bg), contentScale = ContentScale.Crop)
            .fillMaxSize()
    ) {

        // Row with back button and menu button
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
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 10.dp)
        )
        {
            Text(
                "Tap the logo to begin!", style = TextStyle(
                    color = blueColor,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle(R.font.philosopher),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }

}

//@Preview(showBackground = true)
@Composable
fun BreathingAnimation(isStarted: Boolean) {
    TuiLaTheme {
        val strokeWidth = 5.dp
        val activeBarColor = Color.Blue
        val inactiveBarColor = Color.LightGray
        var isInflated by remember {
            mutableStateOf(false)
            //mutableStateOf(isStarted)
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
            //mutableStateOf(false)
            mutableStateOf(isStarted)
        }
        LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
            if (currentTime > 0 && isTimerRunning) {
                delay(1000)
                currentTime -= 1
                value = currentTime / totalTime.toFloat()
            }
        }
        val buttonInteractionSource = remember { MutableInteractionSource() }
        var prompts by remember {
            mutableStateOf(listOf<String>("Breathe In", "Breathe Out"))
        }
        val inflateDeflateTransition = updateTransition(
            targetState = isInflated,
            label = null
        )
        val scaleNotInfinite by animateDpAsState(
            targetValue = if (isInflated) 600.dp else 175.dp,
            animationSpec = repeatable(
                200, animation = tween<Dp>(2500, 1000, easing = Ease),
                RepeatMode.Reverse
            ),
            label = ""
        )
        Column (verticalArrangement = Arrangement.Center){
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    (-200).dp,
                    Alignment.CenterVertically
                ), horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                Box(contentAlignment = Alignment.Center) {
                    Box {
                        Canvas(
                            modifier = Modifier
                        ) {
                            val arcRadius = 500f
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

            }
            Spacer(modifier = Modifier.height(100.dp))
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
        }
    }
}

@Composable
fun BreathingTime(
    totalTime: Int,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp,
    isStarted: Boolean
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        //mutableStateOf(false)
        mutableStateOf(isStarted)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(1000)
            currentTime -= 1
            value = currentTime / totalTime.toFloat()
        }
    }
    Column {
        Box {
            Canvas(
                modifier = Modifier
            ) {
                val arcRadius = 500f
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
    Box {
        Button(
            onClick = {
                if (currentTime <= 0) {
                    currentTime = totalTime
                    isTimerRunning = true
                } else {
                    isTimerRunning = !isTimerRunning
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
        /*Text(
            text = (currentTime).seconds.toString(),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )*/

    }
}