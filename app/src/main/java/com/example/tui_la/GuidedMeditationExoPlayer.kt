package com.example.tui_la

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.WindowManager
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.media3.common.MediaItem.fromUri
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.HttpDataSource
import androidx.media3.datasource.HttpDataSource.HttpDataSourceException
import androidx.media3.datasource.HttpDataSource.InvalidResponseCodeException
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.source.ConcatenatingMediaSource2
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import com.example.tui_la.databinding.LayoutMeditationPlayerBinding
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.json.JSONTokener
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.net.ssl.HttpsURLConnection

//!!DO NOT CHANGE SC_CLIENT_ID OR SC_CLIENT_SECRET.They are necessary to access the SoundCloud API.!!//
private const val SC_Client_ID = "7mveilIe54NH0aS3LBqBdkJuyo8CCvIu"
private const val SC_Client_Secret = "gdzGo4IE4O3AmipQwvu6TGDE52sKBiJY"
private const val Google_Cloud_API_Key = "AIzaSyA6n51QSYLze9UusF2luIrpk3momzDcm3w"
private var accessToken:String=""
private var refreshToken:String=""
val jsonObject = JSONObject()
var httpStreamUrl = ""
var trackId = 314179591
var trackDuration = 660062
private const val TAG = "PlayerActivity"

@UnstableApi class GuidedMeditationExoPlayer: AppCompatActivity(), Player.Listener{
    private lateinit var gmAdManagerAdView: AdManagerAdView
    lateinit var videoPlayer : ExoPlayer
    lateinit var audioPlayer : ExoPlayer
    private lateinit var videoPlayerView: PlayerView
    private lateinit var audioPlayerView: PlayerView
    private val playbackStateListener: Player.Listener = playbackStateListener()
    lateinit var videoView: VideoView
    lateinit var mediaController: MediaController
    private lateinit var playerControlView: PlayerControlView
    private lateinit var audioTitle: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage : FirebaseStorage
    private lateinit var testVid : StorageReference
    private lateinit var listener: ValueEventListener
    private lateinit var binding: LayoutMeditationPlayerBinding
    private var surfaceHolder : SurfaceHolder? = null

    @SuppressLint("NewApi")
    private var currDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) //example: 2023-10-20 as year-month-day with dashes
    @RequiresApi(Build.VERSION_CODES.O)
    private var currTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) //with 24-hr clock, example: 07:02 for AM, 19:02 for PM
    private val viewBinding by lazy ( LazyThreadSafetyMode.NONE ){
        LayoutMeditationPlayerBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        /*binding = LayoutMeditationPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)*/
        //setContentView(R.layout.layout_meditation_player)
        setContentView(viewBinding.root)
        audioTitle = findViewById(R.id.guidedMeditationTitle)
        audioTitle.text = "My Title"
        val backButton : Button = findViewById(R.id.btn_gm_player_back)
        backButton.setBackgroundResource(R.drawable.back_button)

        jsonObject.put("client_id",SC_Client_ID)
        jsonObject.put("client_secret",SC_Client_Secret)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        var storageRef = storage.reference
        var vidRef : StorageReference? = storageRef.child("Guided Meditation Videos")
        val fileName = "large_moon_on_lake_video.mp4"
/*
        val expirationDateRef = database.getReferenceFromUrl("https://tui-la-default-rtdb.firebaseio.com/data/SoundCloud%20Access%20Token/date_acquired")
        expirationDateRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val res = snapshot.getValue<String>()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error","Issue with expirationTimeRef ValueEventListener")
            }
        })
        //val dbSnapshot = expirationTimeRef.get().result.key
        if (currDate != expirationDateRef.get().result.key.toString()){ //expirationDateRef.child("date_acquired").get().result.key
            postAuthorization()
        }
        var stringTime = expirationDateRef.child("time_acquired").get().result.key?.toInt()
        val total = stringTime?.plus(3599)
        if (currTime > total.toString()){
            postAuthorization()
        }
        val testVid = vidRef?.child(fileName)*/
        /*database.getReference("data").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                database.reference.child("SoundCloud Access Token")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })*/
        //Log.i("tokenTime", tokenTime.toString())

        setupPlayer()

        //runBlocking { postAuthorization() }
        accessToken = "2-294264--JhLvzHZ0GwiVlxRda407s2f"
        refreshToken = "NhBrQTECo1xJ2hiU6kwmbZVDxRcJnUIJ"

        runBlocking{
            launch{
                delay(2000)
                streamMusic()
            }
            getStreamingTrack()
        }

        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    }
    private fun postAuthorization(){
        val uriBuilder= Uri.Builder()
            .appendQueryParameter("client_id",SC_Client_ID)
            .appendQueryParameter("client_secret",SC_Client_Secret)
            .appendQueryParameter("grant_type","client_credentials")
            .build()

        val params=uriBuilder.toString().replace("?","") //Remove the "?" from the beginning of the parameters?name=Jack&salary=8054&age=45
        val postData=params.toByteArray(StandardCharsets.UTF_8)

        GlobalScope.launch(Dispatchers.IO){
            val url= URL("https://api.soundcloud.com/oauth2/token")
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.requestMethod="POST"
            httpsURLConnection.setRequestProperty("grant_type","client_credentials")
            httpsURLConnection.setRequestProperty("client_id",SC_Client_ID)
            httpsURLConnection.setRequestProperty("client_secret",SC_Client_Secret)
            httpsURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded") //The format of the content we're sending to the server. Confirmed via SoundCloud Public API Specification.
            httpsURLConnection.setRequestProperty("accept","application/json;charset=utf-8") //The format of response we want to get from the server. Confirmed via SoundCloud Public API Specification.
            httpsURLConnection.doInput=true
            httpsURLConnection.doOutput=true
            val dataOutputStream = DataOutputStream(httpsURLConnection.outputStream)
            dataOutputStream.write(postData)
            dataOutputStream.flush()

            //Check if connection is successful
            val responseCode= httpsURLConnection.responseCode
            if(responseCode == HttpURLConnection.HTTP_OK){
                val reader=httpsURLConnection.inputStream.bufferedReader()
                val response=reader.readText()
                val jsonObject = JSONObject(response)
                accessToken = jsonObject.getString("access_token")
                refreshToken = jsonObject.getString("refresh_token")
                Log.i("ACCESS TOKEN : ",accessToken)
                Log.i("REFRESH TOKEN : ",refreshToken)
                reader.close()
                httpsURLConnection.disconnect()
            }
            else{
                Log.e("HTTPURLCONNECTION_ERROR",responseCode.toString())
                Log.e("ERROR MESSAGE",httpsURLConnection.responseMessage)
                if(responseCode==429){ //Cannot ask for more access tokens because hit the rate_limit
                    val reader=httpsURLConnection.inputStream.bufferedReader()
                    val response=reader.readText()
                    Log.d("RATE LIMIT:",response)
                    reader.close()
                    httpsURLConnection.disconnect()
                }
            }

        }
    }

    private fun getStreamingTrack(){
        GlobalScope.launch(Dispatchers.IO){
            val trackUrl = URL("https://api.soundcloud.com/tracks/${trackId}/streams")
            var trackConn = trackUrl.openConnection() as HttpsURLConnection
            trackConn.requestMethod = "GET"
            trackConn.setRequestProperty("accept","application/json; charset=utf-8")
            trackConn.setRequestProperty("Authorization","OAuth $accessToken")

            trackConn.doInput = true
            trackConn.doOutput = false

            //Check if connection is successful
            val responseCode = trackConn.responseCode
            if(responseCode == HttpURLConnection.HTTP_OK){
                val response= trackConn.inputStream.bufferedReader().readText()
                val jsonObject= JSONTokener(response).nextValue() as JSONObject
                httpStreamUrl = jsonObject.getString("http_mp3_128_url")
                trackConn.disconnect()

                //Uncomment line below if need to print parsed string to the log
                //Log.i("STREAMURL:", httpStreamUrl)

                /*Shouldn't need these, but these are the 3 other urls returned from SoundCloud
                myHls1=jsonObject.getString("hls_mp3_128_url")
                val myHls2=jsonObject.getString("hls_opus_64_url")
                val myPrev=jsonObject.getString("preview_mp3_128_url")*/
            }
            else {
                Log.e("Track Streaming Error",responseCode.toString())
                Log.e("ERROR MESSAGE:",trackConn.responseMessage)
                trackConn.disconnect()
            }
        }
    }

    private fun streamMusic(){
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        val progressiveMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(fromUri(httpStreamUrl))
        var vidSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(fromUri("https://firebasestorage.googleapis.com/v0/b/tui-la.appspot.com/o/Guided%20Meditation%20Videos%2Flarge_moon_on_lake_video.mp4?alt=media&token=a5b04e55-0fd1-457c-a9dd-83c0e7265371&_gl=1*dtfdkc*_ga*MTM5MDAzMTAzNi4xNjkyMzIxMjc4*_ga_CW55HF8NVT*MTY5NzEzMzM0NS4xOC4xLjE2OTcxMzUwMzcuNjAuMC4w"))
        val loopVideo = ConcatenatingMediaSource2.Builder()
            .add(vidSource, trackDuration.toLong())
            .add(vidSource, trackDuration.toLong())
            .add(vidSource, trackDuration.toLong())
            .build()

        val mergeSource = MergingMediaSource(progressiveMediaSource,vidSource)

        audioPlayer.setMediaSource(progressiveMediaSource)

        videoPlayer.setMediaSource(vidSource)

        videoPlayer.prepare()
        videoPlayer.play()

        audioPlayer.prepare()
        audioPlayer.play()

    }

    /*override fun onSaveInstanceState(outState:Bundle){
        super.onSaveInstanceState(outState)
        outState.putLong("seekTime",audioPlayer.currentPosition)
        outState.putInt("mediaItem",audioPlayer.currentMediaItemIndex)
    }*/

    @SuppressLint("UnsafeOptInUsageError")
    private fun setupPlayer(){
        val renderersFactory = buildRenderersFactory(this,true)
        val mediaSourceFactory = DefaultMediaSourceFactory(getDataSourceFactory(this),DefaultExtractorsFactory())
        val trackSelector = DefaultTrackSelector(this)
        videoPlayer = ExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .setRenderersFactory(renderersFactory)
            .setMediaSourceFactory(mediaSourceFactory)
            .setUseLazyPreparation(true)
            .build().apply{
                trackSelectionParameters = DefaultTrackSelector.Parameters.getDefaults(applicationContext)
                playWhenReady=false
            }
        videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        videoPlayerView = findViewById(R.id.gm_player_video)
        var vidHeight = videoPlayerView.height

        //videoPlayerView.layoutParams.height = vidHeight
        videoPlayerView.updateLayoutParams { height = window.attributes.height - 55 }

        videoPlayerView.player = videoPlayer

        audioPlayer = ExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .setRenderersFactory(renderersFactory)
            .setMediaSourceFactory(mediaSourceFactory)
            .setUseLazyPreparation(true)
            .build().apply{
                trackSelectionParameters = DefaultTrackSelector.Parameters.getDefaults(applicationContext)
                playWhenReady=false
            }
        audioPlayerView = findViewById(R.id.gm_player_audio)
        audioPlayerView.player = audioPlayer
        audioPlayerView.setBackgroundColor(Color.TRANSPARENT)
        audioPlayerView.showController()
        audioPlayerView.setShowRewindButton(true)
        audioPlayerView.setShowFastForwardButton(true)

        val audioPlayerListener = audioPlayer.addListener(
            object: Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if (isPlaying) {
                        //active playback
                    } else {
                        // Not playing because playback is paused, ended, suppressed, or the player
                        // is buffering, stopped or failed. Check player.playWhenReady,
                        // player.playbackState, player.playbackSuppressionReason and
                        // player.playerError for details.
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    val cause = error.cause
                    if (cause is HttpDataSourceException){
                        //An HTTP error occurred.
                        val httpError = cause
                        //It's possible to find out more about the error both by casting and querying the cause
                        if (httpError is InvalidResponseCodeException){
                            // Cast to InvalidResponseCodeException and retrieve the response code, message
                            // and headers.
                        } else {
                            // Try calling httpError.getCause() to retrieve the underlying cause, although
                            // note that it may be null.
                        }
                    }
                }
            }
        )

        //videoPlayer.addListener(this)
    }
    private fun buildRenderersFactory(context: Context, preferExtensionRenderer:Boolean
    ): RenderersFactory {
        val extensionRendererMode=if(preferExtensionRenderer)
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
        else DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON

        return DefaultRenderersFactory(context)
            .setExtensionRendererMode(extensionRendererMode)
            .setEnableDecoderFallback(true)
    }
    private fun getDataSourceFactory(context:Context): DataSource.Factory=
    DefaultDataSource.Factory(context,getHttpDataSourceFactory())

    private fun getHttpDataSourceFactory(): HttpDataSource.Factory{
        return DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
    }
private fun playbackStateListener() = object : Player.Listener {
    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        val stateString: String = when (playbackState) {
            Player.STATE_IDLE -> "Player.STATE_IDLE      -"
            Player.STATE_BUFFERING -> "Player.STATE_BUFFERING -"
            Player.STATE_READY -> "Player.STATE_READY     -"
            Player.STATE_ENDED -> "Player.STATE_ENDED     -"
            else -> "UNKNOWN_STATE             -"
        }
        Log.d(TAG, "changed player state to $stateString")
    }
}

    override fun onStop(){
        super.onStop()
       // audioPlayer.playWhenReady=false
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        audioPlayer.removeListener(playbackStateListener)
        //videoPlayer.removeListener(playbackStateListener)
        //videoPlayer.release()
        audioPlayer.release()
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata){
        super.onMediaMetadataChanged(mediaMetadata)
        audioTitle.text=mediaMetadata.title?:mediaMetadata.displayTitle?: "Title not found"
    }
}
