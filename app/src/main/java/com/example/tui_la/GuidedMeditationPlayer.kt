package com.example.tui_la

import android.media.MediaDescription
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.provider.MediaStore.Audio.Media
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.DefaultHlsDataSourceFactory
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView

class GuidedMeditationPlayer : AppCompatActivity() {
    lateinit var playerView: PlayerView
    lateinit var player: ExoPlayer
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_player)
        playerView = findViewById(R.id.guidedMeditationAudioPlayer)


        var source = MediaSource
        val player = ExoPlayer.Builder(this).build()
            .setMediaSource()

        playerView.player = player
        /*val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        var mediaList = mutableListOf<Int>()
        mediaList.add(R.raw.basic_relaxation_10mins)
        mediaList.add(R.raw.allowing_uncertainty_10mins)
        mediaList.add(R.raw.ocean_breathing_10mins)*/
        MediaDescription desc = new MediaDescription.Builder()
            .setMediaUri()
            .build()

        //Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(hlsUri))
        val mediaItem = androidx.media3.common.MediaItem.Builder()
            .setMimeType(HlsMediaSource.)
            .setUri(R.raw.basic_relaxation_10mins)
            .build()
        val hlsMediaSource = HlsMediaSource.Factory(MediaItem.Builder())

        /*val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(this)
        ).createMediaSource(mediaItem)*/
        player.addMediaItem(mediaItem)

        player.prepare()
        player.play()
    }
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