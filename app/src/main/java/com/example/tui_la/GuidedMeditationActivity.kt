package com.example.tui_la

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView

class GuidedMeditationActivity : AppCompatActivity(), GuidedMeditationAdapter.RecyclerViewEvent{
    private val data = createData()
    lateinit var gmAdManagerAdView : AdManagerAdView
    var gmMediaPlayer: MediaPlayer? = null

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
        val gmImg = data[position]

        runPlayer()
        //Toast.makeText(this,gmImg.name,Toast.LENGTH_SHORT).show()
    }

    private fun createData():ArrayList<GuidedMeditationDataClass>{
        val titles = GuidedMeditationDataRepo.gmNames
        val aud = GuidedMeditationDataRepo.gmAud
        val img = GuidedMeditationDataRepo.gmImg
        val gmData = ArrayList<GuidedMeditationDataClass>()

        for (i in 0..4){
            gmData.add(i,GuidedMeditationDataClass(titles[i],aud[i],img[i]))
        }
        return gmData
    }

    fun runPlayer() {
        setContentView(R.layout.layout_meditation_player)
        val btn: ImageButton = findViewById(R.id.playButton)
        fun playSound() {
            if (gmMediaPlayer?.isPlaying == true) {
                gmMediaPlayer?.pause()
            }
            if (gmMediaPlayer == null) {
                gmMediaPlayer = MediaPlayer.create(this, R.raw.ocean_breathing_10mins)
                gmMediaPlayer!!.isLooping = true
                gmMediaPlayer!!.start()
            } else gmMediaPlayer!!.start()
        }

        fun pauseSound() {
            if (gmMediaPlayer?.isPlaying == true) gmMediaPlayer?.pause()
        }

        fun stopSound() {
            if (gmMediaPlayer != null) {
                gmMediaPlayer!!.stop()
                gmMediaPlayer!!.release()
                gmMediaPlayer = null
            }
        }

       /* override fun onStop() {
            super.onStop()
            if (gmMediaPlayer != null) {
                gmMediaPlayer!!.release()
                gmMediaPlayer = null
            }
        }*/
    }
}
