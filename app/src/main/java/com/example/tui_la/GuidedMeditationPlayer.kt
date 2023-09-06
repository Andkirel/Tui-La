package com.example.tui_la

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity

class GuidedMeditationPlayer : AppCompatActivity() {
    var gmMediaPlayer: MediaPlayer? = null

    fun playSound(){
        if (gmMediaPlayer?.isPlaying == true){
            gmMediaPlayer?.pause()
        }
        if (gmMediaPlayer == null){
            gmMediaPlayer = MediaPlayer.create(this,R.raw.ocean_breathing_10mins)
            gmMediaPlayer!!.isLooping = true
            gmMediaPlayer!!.start()
        } else gmMediaPlayer!!.start()
    }

    fun pauseSound(){
        if (gmMediaPlayer?.isPlaying == true) gmMediaPlayer?.pause()
    }

    fun stopSound() {
        if (gmMediaPlayer != null) {
            gmMediaPlayer!!.stop()
            gmMediaPlayer!!.release()
            gmMediaPlayer = null
        }
    }

    override fun onStop(){
        super.onStop()
        if (gmMediaPlayer != null){
            gmMediaPlayer!!.release()
            gmMediaPlayer = null
        }
    }


    /*lateinit var playerView: PlayerView
    lateinit var player: ExoPlayer
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_player)
        playerView = findViewById(R.id.guidedMeditationAudioPlayer)


        var source = MediaSource
        val player = ExoPlayer.Builder(this).build()
            .setMediaSource()

        playerView.player = player
        *//*val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        var mediaList = mutableListOf<Int>()
        mediaList.add(R.raw.basic_relaxation_10mins)
        mediaList.add(R.raw.allowing_uncertainty_10mins)
        mediaList.add(R.raw.ocean_breathing_10mins)*//*
        MediaDescription desc = new MediaDescription.Builder()
            .setMediaUri()
            .build()

        //Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(hlsUri))
        val mediaItem = androidx.media3.common.MediaItem.Builder()
            .setMimeType(HlsMediaSource.)
            .setUri(R.raw.basic_relaxation_10mins)
            .build()
        val hlsMediaSource = HlsMediaSource.Factory(MediaItem.Builder())

        *//*val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(this)
        ).createMediaSource(mediaItem)*//*
        player.addMediaItem(mediaItem)

        player.prepare()
        player.play()
    }*/
    /*private val audUris = emptyList<Uri>()
    val player: Player

    val audList = audUris.map { audUris ->
        audUris.map {audUris -> VideoItem(
            contentUri = audUris,
            mediaItem = MediaItem.fromUri()
            name = ""
    )}}

    init{
        player.prepare()
    }
    fun addAudio(aud: Int){
        player.addMediaItem(MediaItem.fromUri(audUris))
    }
    fun playAud(uri: Uri){
        player.setMediaItem(

        )
    }*/
}