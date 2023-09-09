package com.example.tui_la

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView

class GuidedMeditationActivity : AppCompatActivity(),
    GuidedMeditationAdapter.RecyclerViewEvent {
    private val data = createData()
    private lateinit var gmAdManagerAdView : AdManagerAdView

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
    /////!!!!!!!!!!!!!!!    KEEP THIS!  !!!!!!//////////
    override fun onItemClick(position: Int) {
        val userPick = data[position]
        Toast.makeText(this,userPick.name,Toast.LENGTH_SHORT).show()
    }
    /////!!!!!!!!!!!!!!!    KEEP THIS!  !!!!!!//////////
    override fun onClick(v: View?) {

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
