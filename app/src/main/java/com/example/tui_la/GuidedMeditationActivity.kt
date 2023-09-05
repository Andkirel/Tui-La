package com.example.tui_la

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView

class GuidedMeditationActivity : AppCompatActivity(), GuidedMeditationAdapter.RecyclerViewEvent{
    private val data = createData()
    lateinit var gmAdManagerAdView : AdManagerAdView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_selection)

        MobileAds.initialize(this)
        gmAdManagerAdView = findViewById(R.id.gmLayAdManagerAdView)
        val adRequest = AdManagerAdRequest.Builder().build()
        gmAdManagerAdView.loadAd(adRequest)

        val recyclerView: RecyclerView = findViewById(R.id.gm_recyclerview)
        recyclerView.adapter = GuidedMeditationAdapter(data,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(position: Int) {
        val gmName = data[position]
        Toast.makeText(this,gmName.name,Toast.LENGTH_SHORT).show()
    }

    private fun createData():ArrayList<GuidedMeditationDataClass>{
        val titles = GuidedMeditationDataRepo.gmNames
        val aud = GuidedMeditationDataRepo.gmAud
        val img = GuidedMeditationDataRepo.gmImg
        val gmData = ArrayList<GuidedMeditationDataClass>()

        for (i in 0..2){
            gmData.add(i,GuidedMeditationDataClass(titles[i],aud[i],img[i]))
        }

        return gmData
    }


}
