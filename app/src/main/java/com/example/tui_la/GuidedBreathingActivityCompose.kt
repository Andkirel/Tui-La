package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
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
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tui_la.ui.theme.TuiLaTheme

class GuidedBreathingActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TuiLaTheme {
                //A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier
                        .paint(painterResource(id = R.drawable.main_bg)),
                ) {*/
                    CreateLayout()

                    //Greeting("Android")
               //}
                //BreathingAnimation()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CreateLayout(modifier: Modifier = Modifier) {
    val purpleColor = colorResource(id = R.color.cactus_purple)
Column (modifier = Modifier
    .paint(painterResource(id = R.drawable.main_bg), contentScale = ContentScale.Crop)
    .fillMaxSize()){
    Row (Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top){
        Box(modifier = Modifier, contentAlignment = Alignment.TopStart){
            Button(onClick = { /*TODO*/ },modifier = Modifier
                .size(75.dp, 75.dp)
                .background(Color.Transparent),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Black)
            ) {
                Image(painter = painterResource(id = R.drawable.back_button), contentDescription = "",Modifier.fillMaxSize())
            }
        }
        Spacer(modifier = Modifier
            .size(40.dp).width(IntrinsicSize.Max).height(IntrinsicSize.Max).weight(1f))
        Box(modifier = Modifier, contentAlignment = Alignment.TopEnd){
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(75.dp, 75.dp)
                    .background(Color.Transparent),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Black),
            ) {
                Image(painter = painterResource(id = R.drawable.menu_dots), contentDescription = "Menu dots button",Modifier.fillMaxSize())
            }
        }
    }
    Box(modifier = Modifier
        //.paint(painterResource(id = R.drawable.main_bg), contentScale = ContentScale.Crop)
        //.fillMaxSize()
        .align(Alignment.CenterHorizontally)
        .padding(0.dp,20.dp))
    {
            Text("Tap the logo to begin!", style = TextStyle(
                color = purpleColor,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle(R.font.philosopher),
                textAlign = TextAlign.Center),
                modifier = Modifier.align(Alignment.TopCenter))
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
//@Preview(showBackground = true)
@Composable
fun BreathingAnimation() {
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
        val tryMe = sizeTransition
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
//                        prompts[1] != prompts[2]
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