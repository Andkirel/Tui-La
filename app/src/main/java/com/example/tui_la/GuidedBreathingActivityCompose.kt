package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tui_la.ui.theme.TuiLaTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

enum class lengthOptions(val seconds: Int){
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
            Surface (color = Color.White, modifier = Modifier.fillMaxSize()){
                //Box(contentAlignment = Alignment.Center){
                    BreathingTime(totalTime = lengthOptions.fiveMinutes.seconds,
                        inactiveBarColor = Color.LightGray,
                        activeBarColor = Color.Blue,
                        modifier = Modifier.size(300.dp))
                //}
            }

                /*CreateLayout()
                Column {
                    Spacer(modifier = Modifier.height(100.dp))
                    BreathingAnimation()*/

                    /*var count by remember {
                        mutableStateOf(0)
                    }
                    AnimatedCounter(
                        count = count,
                        style = MaterialTheme.typography.bodyMedium
                    )*/
                    /*CircularProgress(modifier = Modifier
                        .width(150.dp)
                        .height(150.dp),
                        progress = 10f,
                        color = Color.Blue,
                        backgroundColor = Color.LightGray
                    )*/



                }
            }
        }
    //}

@Composable
fun CircularProgressBarTimer(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 35.sp,
    radius: Dp = 75.dp,
    color: Color = Color.Blue,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
){
    var animationPlayed by remember{
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        label = ""
    )


}
// region CircularProgress Commented out
/*
@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    color: Color,
    backgroundColor: Color = color,
    startingAngle: Float = 270f,
    progress: Float,
    animate: Boolean = true,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
) {
    val darkMode = isSystemInDarkTheme()
    val animatedProgress: Float by animateFloatAsState(targetValue = progress, animationSpec = animationSpec)
    Canvas(modifier) {
        val sweepAngle = (360 * if (animate) animatedProgress else progress) / 100
        val ringRadius = size.minDimension * 0.15f
        val size = Size(size.width, size.height)
        drawArc(backgroundColor, startingAngle, 360f, false, size = size, alpha = 0.2f, style = Stroke(ringRadius))
        drawArc(
            color = if (darkMode) Color.White else Color.Black,
            startingAngle, sweepAngle, false, Offset(0f, 20f), size, 0.2f, Stroke(ringRadius, cap = StrokeCap.Round)
        )
        drawArc(color, startingAngle, sweepAngle, false, size = size, style = Stroke(ringRadius, cap = StrokeCap.Round))
    }
}
*/

/*@ExperimentalFoundationApi
@Composable
@Preview
private fun MockCountdownTimer() {
    AppTheme {
        val rows = listOf(
            Pair(0f, MaterialTheme.colors.primary),
            Pair(5f, MaterialTheme.colors.primary),
            Pair(10f, MaterialTheme.colors.primary),
            Pair(15f, MaterialTheme.colors.primary),
            Pair(20f, MaterialTheme.colors.primary),
            Pair(25f, MaterialTheme.colors.primary),
            Pair(30f, MaterialTheme.colors.primary),
            Pair(35f, MaterialTheme.colors.primary),
            Pair(40f, MaterialTheme.colors.primary),
            Pair(45f, MaterialTheme.colors.primary),
            Pair(50f, MaterialTheme.colors.primary),
            Pair(55f, MaterialTheme.colors.primary),
            Pair(60f, MaterialTheme.colors.primary),
            Pair(65f, MaterialTheme.colors.primary),
            Pair(70f, MaterialTheme.colors.primary),
            Pair(75f, MaterialTheme.colors.primary),
            Pair(80f, MaterialTheme.colors.primary),
            Pair(85f, MaterialTheme.colors.primary),
            Pair(90f, MaterialTheme.colors.primary),
            Pair(95f, MaterialTheme.colors.primary),
            Pair(100f, MaterialTheme.colors.primary)
        )
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), cells = GridCells.Adaptive(minSize = 130.dp)) {
            items(rows) {
                Box(Modifier.padding(20.dp), contentAlignment = Alignment.Center) {
                    CircularProgress(
                        Modifier
                            .width(80.dp)
                            .height(80.dp),
                        progress = it.first, color = it.second
                    )
                }
            }
        }
    }
}*/
//endregion

@Composable
//@Preview(showBackground = true)
fun CreateLayout(modifier: Modifier = Modifier) {
    val purpleColor = colorResource(id = R.color.cactus_purple)
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
                .padding(0.dp, 20.dp)
        )
        {
            Text(
                "Tap the logo to begin!", style = TextStyle(
                    color = purpleColor,
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
//@Preview(showBackground = true)
@Composable
fun BreathingAnimation() {
    TuiLaTheme {
        var isInflated by remember {
            mutableStateOf(false)
        }
        val buttonInteractionSource = remember { MutableInteractionSource() }
        var prompts by remember {
            mutableStateOf(listOf<String>("Breathe In", "Breathe Out"))
        }
        val inflateDeflateTransition = updateTransition(
            targetState = isInflated,
            label = null
        )
        val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
        val scaleInfinite by infiniteTransition.animateFloat(
            initialValue = 0.75f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                tween(3000, delayMillis = 1500),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )
        val scaleNotInfinite by animateDpAsState(
            targetValue = if (isInflated) 600.dp else 175.dp,
            animationSpec = repeatable(
                200, animation = tween<Dp>(2500, 1000, easing = Ease),
                RepeatMode.Reverse
            ),
            label = ""
        )

        Column {
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

@Composable
fun BreathingTime(
    totalTime: Int,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp
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
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(1000)
            currentTime -= 1
            value = currentTime / totalTime.toFloat()
        }
    }
    Column (verticalArrangement = Arrangement.spacedBy(50.dp,Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally){
        Box(contentAlignment = Alignment.Center
            /*modifier = Modifier
                .onSizeChanged { size = it }*/
        ) {
            Canvas(modifier = Modifier//.scale(1f)/*.align(alignment = Alignment.Center)*/
            ) {
                val arcRadius = 500f
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawArc(
                    color = inactiveBarColor,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    //size = Size(500f,500f),//Size(size.width.toFloat(), size.height.toFloat()),
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
                    //size = Size(500f,500f),//Size(size.width.toFloat(), size.height.toFloat()),
                    size = Size(arcRadius, arcRadius),
                    topLeft = Offset(
                        (canvasWidth / 2) - (arcRadius / 2),
                        canvasHeight / 2 - (arcRadius / 2)
                    ),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
        }
        //Spacer(modifier = Modifier.height(50.dp))
        Box(/*contentAlignment = Alignment.Center,*/
           /* modifier = Modifier.onSizeChanged { size = it }*/) {
            Button(
                onClick = {
                    if (currentTime <= 0) {
                        currentTime = totalTime
                        isTimerRunning = true
                    } else {
                        isTimerRunning = !isTimerRunning
                    }
                },
                /*modifier = Modifier.align(Alignment.Center),*/
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
        //Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = (currentTime).seconds.toString(),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    var oldCount by remember {
        mutableStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val countString = count.toString()
        val oldCountString = oldCount.toString()
        for(i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if(oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } with slideOutVertically { -it }
                }, label = ""
            ) { char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}