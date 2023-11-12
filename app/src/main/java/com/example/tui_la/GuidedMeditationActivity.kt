package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView

class GuidedMeditationActivity : AppCompatActivity(),
    GuidedMeditationAdapter.RecyclerViewEvent {
    private val data = createData()
    private lateinit var composeView: ComposeView
    private lateinit var gmAdManagerAdView : AdManagerAdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_selection)

        MobileAds.initialize(this)
        gmAdManagerAdView = findViewById(R.id.gmLayAdManagerAdView)
        val adRequest = AdManagerAdRequest.Builder().build()
        gmAdManagerAdView.loadAd(adRequest)

        composeView = findViewById(R.id.compose_view)

        composeView.setContent {
            LazyColumn(state = rememberLazyListState()) {
                items(data) {
                    ListItem(it)
                }
            }
    }

        /*val recyclerView: RecyclerView = findViewById(R.id.gm_recyclerview)
        recyclerView.adapter = GuidedMeditationAdapter(data,this)
        recyclerView.layoutManager = LinearLayoutManager(this)*/
    }
    @Composable
    fun ListItem(data: GuidedMeditationDataClass,modifier: Modifier = Modifier){
        Card(modifier = Modifier
            .padding(5.dp)
            .height(126.dp), elevation = CardDefaults.elevatedCardElevation(5.dp)) {
            Row (
                modifier
                    .fillMaxSize()
                    .clickable(onClick = {
                        Toast
                            .makeText(applicationContext, data.name, Toast.LENGTH_SHORT)
                            .show()
                    })){
                Image(painterResource(id = data.img), contentDescription = "", alignment = Alignment.Center)
                /*Text(text=data.name, modifier = Modifier.shadow(5.dp, shape = RectangleShape,
                        clip = false, ambientColor = colorResource(id = R.color.white)),
                    FontFamily = "@font/philosopher", Color = "#a4133c", TextAlign = TextAlign.End,
                    softWrap = true, TextStyle = "bold")*/
                Text(text = data.name)
            }

        }

    }
    /////!!!!!!!!!!!!!!!    KEEP THIS!  !!!!!!//////////
    override fun onItemClick(position: Int) {
        val userPick = data[position]
        Toast.makeText(this,userPick.name,Toast.LENGTH_SHORT).show()
    }
    /////!!!!!!!!!!!!!!!    KEEP THIS!  !!!!!!//////////
    override fun onClick(v: View?) {
        val intent = Intent(this, GuidedMeditationPlayer::class.java)
        startActivity(intent)
        Toast.makeText(this,"Maybe",Toast.LENGTH_SHORT).show()
    }

    private fun createData():List<GuidedMeditationDataClass>{
        val titles = GuidedMeditationDataRepo.gmNames
        val aud = GuidedMeditationDataRepo.gmAud
        val img = GuidedMeditationDataRepo.gmImg
        val vids = GuidedMeditationDataRepo.gmVid

        val gmData = ArrayList<GuidedMeditationDataClass>()

        for (i in 0..4){
            gmData.add(GuidedMeditationDataClass(titles[i],aud[i],img[i],vids[i]))
        }
        return gmData
    }

}
