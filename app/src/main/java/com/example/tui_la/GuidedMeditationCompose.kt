package com.example.tui_la

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GuidedMeditationCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
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
fun MyApp() {
    val meditations = remember { DataProvider.gmDataList }
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(
            items = meditations,
            itemContent = {
                GmListItem(meditationData = it)
            })
    }
}

object DataProvider {
    val gmDataList = listOf(
        GuidedMeditationData(
            trackName = "Appreciative Joy",
            trackId = 314179591,
            gmImages = R.drawable.gm_still_morning_river_sunbeams,
            audIds = "1LkHp3yXtwVxVFUaqDPK41MZE-JE8cVjc",
            vidIds = "1aFoBLtg2I8vJRQRnNzVn4WbSMgZUZWw1"
        ),
        GuidedMeditationData(
            trackName = "Guided Sitting Meditation",
            trackId = 1016402329,
            gmImages = R.drawable.gm_still_waterfalls_over_pond_facing_right,
            audIds = "12CflTXe6_8KKRYOYq0Ssx41lpQUsJXip",
            vidIds = "1haGwmAqQ5o8SECfCfJFIwG-uKMActRal"
        ),
        GuidedMeditationData(
            trackName = "Compassion Meditation",
            trackId = 1174448758,
            gmImages = R.drawable.gm_still_waves_from_above,
            audIds = "1vvYq0ie0QmNvgHpLeQMNYyZUNGikjeyf",
            vidIds = "1vdd9uOXsdlCbtFRUxFELa4i06I3bIZTv"
        ),
        GuidedMeditationData(
            trackName = "Meditation for Difficult Emotions",
            trackId = 1074950746,
            gmImages = R.drawable.gm_still_slow_waves_closeup,
            audIds = "1uW0z7nqH6Yn0ADf9FT42kGJdP1Y6X_5P",
            vidIds = "1v7n378ZnsAdtiWOMpZ2YQC4CFnbrGj-r"
        ),
        GuidedMeditationData(
            trackName = "Working with Difficult Opposing Council",
            trackId = 683387639,
            gmImages = R.drawable.gm_still_large_moon_over_lake_resized,
            audIds = "1vdYbTCZ57r-jsX9MoAzlOnMJJ7n3UcYU",
            vidIds = "1RMdKtJUQoYA4t1KfycAuq24QizkKemx7"
        ),
        GuidedMeditationData(
            trackName = "Breath Control",
            trackId = 796953958,
            gmImages = R.drawable.gm_still_morning_teacup_steam_resized,
            audIds = "1KFb5qLgF0DcDycDGKkWQBQmFgduRP0XR",
            vidIds = "1SZgUNAiUKIArJE_J3P9taC0ONK3rGWAl"
        ),
        GuidedMeditationData(
            trackName = "Seedling",
            trackId = 314179591,
            gmImages = R.drawable.gm_still_windy_meadow,
            audIds = "1sdE8as0owVMP7T8vsXL4ZDJg_zBJhVbY",
            vidIds = "1K-zIIxEb0E7TcsKdZ2AEEuoo-CxWO7A1"
        ),
    )
}

@Composable
fun GmListItem(meditationData: GuidedMeditationData) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .height(126.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.CenterVertically),
        /*.clickable(onClick = {
            Toast
                .makeText(applicationContext, data.trackName, Toast.LENGTH_SHORT)
                .show()
        })*/
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                Image(painter = painterResource(id = meditationData.gmImages), contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .wrapContentSize(),
                        contentScale = ContentScale.FillBounds, alignment = Alignment.Center)
                Text(text = meditationData.trackName, modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(5.dp).shadow(4.dp, clip = true),
                    style = TextStyle(color = colorResource(id = R.color.light_salmon),
                        shadow = Shadow(color = Color.Black, offset = Offset(3f,3f), blurRadius = 5f),
                        fontSize = 30.sp,
                    ), fontStyle = FontStyle(R.font.philosopher_bold), textAlign = TextAlign.Right,
                    fontFamily = FontFamily(Font(R.font.philosopher)),
                )
            }


        }
    }

}