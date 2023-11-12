package com.example.tui_la

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class GuidedMeditationPlayer : AppCompatActivity(), Player.Listener{
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var audioTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_player)
        audioTitle = findViewById(R.id.guidedMeditationTitle)
        setupPlayer()
        addMp4Files()

    }

    private fun setupPlayer(){
        player = ExoPlayer.Builder(this).build()
        playerView = findViewById(R.id.gm_player_audio)
        playerView.player = player
        player.addListener(this)
    }

    private fun addMp4Files(){
//        val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://" + packageName+ "/"+ R.raw.basic_relaxation_10mins))
//        player.addMediaItem(mediaItem)
//        player.prepare()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when(playbackState){
            Player.STATE_BUFFERING -> {
                //make progressbar visible
            }
            Player.STATE_READY -> {
                //make progress bar invisible
            }
        }
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onMediaMetadataChanged(mediaMetadata)
        audioTitle.text = mediaMetadata.title ?: mediaMetadata.displayTitle ?: "Title not found"
    }
}