package com.example.tui_la

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView

@UnstableApi class GuidedMeditationActivity : AppCompatActivity(),
    GuidedMeditationAdapter.RecyclerViewEvent {
    private val data = createData()
    private lateinit var gmAdManagerAdView : AdManagerAdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_guided_meditation)

        MobileAds.initialize(this)
        gmAdManagerAdView = findViewById(R.id.gmLayAdManagerAdView)
        val adRequest = AdManagerAdRequest.Builder().build()
        gmAdManagerAdView.loadAd(adRequest)

        val recyclerView: RecyclerView = findViewById(R.id.gm_recyclerview)
        recyclerView.adapter = GuidedMeditationAdapter(data,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    /////!!!!!!!!!!!!!!!    KEEP THIS!  !!!!!!//////////
    override fun onItemClick(position: Int) {
        val userPick = data[position]
        Toast.makeText(this,userPick.trackName,Toast.LENGTH_SHORT).show()
    }

    /////!!!!!!!!!!!!!!!    KEEP THIS!  !!!!!!//////////
    //TODO: Look into adding ViewAnimator to pop open the player?
    override fun onClick(v: View?) {
        val intent = Intent(this, GuidedMeditationExoPlayer::class.java)
        startActivity(intent)
        Toast.makeText(this,"Maybe",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun createData():List<GuidedMeditationData>{
        val title = GuidedMeditationDataRepo.gmNames
        val trackId = GuidedMeditationDataRepo.soundcloudTrackID
        val img = GuidedMeditationDataRepo.gmImgs

        val gmData = ArrayList<GuidedMeditationData>()
        for (i in 0..3){
            gmData.add(GuidedMeditationData(title[i],trackId[i],img[i]))
        }
        return gmData
    }

}
