package com.example.tui_la

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
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
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@UnstableApi class GuidedMeditationActivity : AppCompatActivity(),
    GuidedMeditationAdapter.RecyclerViewEvent {
    private val data = createData()
    private lateinit var composeView: ComposeView
    private lateinit var listener: OnClickListener
    private lateinit var gmAdManagerAdView : AdManagerAdView
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    @SuppressLint("NewApi")
    private var currTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_guided_meditation)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        //var expired = database.getReference("data").child("SoundCloud Access Token").child("expires_in")

        Log.i("Current Date Time",currTime.toString())
        //Log.i("Firebase Expires In",expired.toString())

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
    //    KEEP THIS!    //
    override fun onItemClick(position: Int) {
        val userPick = data[position]
        when (position) {
            0 -> Toast.makeText(this,userPick.trackName,Toast.LENGTH_SHORT).show()
            1 -> startActivity(Intent(this,GuidedMeditationExoPlayer::class.java))
        }
    }

    /*//    KEEP THIS!    //
    override fun onClick(v: View?) {
        listener.onClick(v).run {
            Toast.makeText(parent.baseContext, "Please work", Toast.LENGTH_SHORT).show()
        }

        *//*val intent = Intent(this, GuidedMeditationExoPlayer::class.java)
        startActivity(intent)
        Toast.makeText(this,"Loading, please wait...",Toast.LENGTH_SHORT).show()*//*
    }
*/
    @SuppressLint("UnsafeOptInUsageError")
    private fun createData():List<GuidedMeditationData>{
        val title = GuidedMeditationDataRepo.gmNames
        val trackId = GuidedMeditationDataRepo.soundcloudTrackID
        val img = GuidedMeditationDataRepo.gmImgs
        val audIds = GuidedMeditationDataRepo.gmGoogleAudIds
        val vidIds = GuidedMeditationDataRepo.gmGoogleVidIds

        val gmData = ArrayList<GuidedMeditationData>()
        for (i in 0..5){
            gmData.add(
                GuidedMeditationData(
                    title[i],
                    trackId[i],
                    img[i],
                    audIds[i],
                    vidIds[i]
                )
            )
        }
        return gmData
    }

}
