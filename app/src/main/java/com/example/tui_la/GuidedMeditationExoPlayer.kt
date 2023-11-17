package com.example.tui_la

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem.fromUri
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.Player.EVENT_IS_PLAYING_CHANGED
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.drive.DriveScopes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.json.JSONTokener
import java.io.DataOutputStream
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import javax.net.ssl.HttpsURLConnection


//!!DO NOT CHANGE THE BELOW STRINGS. They are necessary to access the SoundCloud API AND Google Drive APIs.!!//
private const val SC_Client_ID = "7mveilIe54NH0aS3LBqBdkJuyo8CCvIu"
private const val SC_Client_Secret = "gdzGo4IE4O3AmipQwvu6TGDE52sKBiJY"
private const val Google_Cloud_API_Key = "AIzaSyCct_GWWBsyE9UEdfDhzNi_zWqAr3z2SlY"
private const val Tui_La_Service_Email =
    "tui-la-admin@tui-la-guided-meditation.iam.gserviceaccount.com"
private const val Tui_La_Service_Key_ID = "bb19377f0716e67b35ed30e309f3c51e4a8fee2f"
private const val Tui_La_Service_OAuth2_Client_ID = "117055122084581803157"
private const val Tui_La_Google_OAuth2_Client_ID = "156633646436-olagt48bvdajeu9jvibkh840no7nt53f"
private const val Tui_La_Google_Client_ID_Web_Application =
    "156633646436-t251lu3n50kpmnstfham4ubj8pd34in9.apps.googleusercontent.com"
private const val Tui_La_Google_Client_Web_Client_Secret = "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"
private const val Tui_La_Google_Authorized_Redirect_Uri = "https://tui-la.com/callback"
private const val Google_Authorization_Code = "ya29.a0AfB_byAua2eZiPUySdgoYbS69sk7e3hCtlRQOL340KH5Rwi1WG7-lb1i2inUjARkhp8Z6JMY9MAuStHHA-b5RMpz5t8OVvunR9OJ0uzn_PSS6RF3CEeBSoosIB1faCwnL5OniJZMBy9qaTF5wYwI8Qddury5rz06wIP0NgANOXQW-moC3dvP-r58R-Ngcy2fk7sm0RO9kxUuV7axGTHc1VodMZpWZBT6wEoyGREa7MU2jV-wklDKTnZhtIg7VcjgtWIuoqS3axWl0iC6uOJbNDYQ5PV7-AkPY662q5J8aOfsbnz2WPCs-BuYwO4Ul_FPrm9vdRwGk84m4j-dkSqAL-ikNhiXAi-GfZrLyEIdXXKqJx3FwgNhAg3oLduDSK3GL-1qKfH4jRh7zdOR9E4AU-MAf2g2bmcaCgYKAd4SARASFQHGX2Mi3fW3tgtKI10hLcRDqNPyjg0422"

var accessToken = ""
var refreshToken = ""
var httpStreamUrl = ""
var trackId = 1074950746
val credentialsFile = FileInputStream("gdrive-tui-la-admin")

private const val TAG = "PlayerActivity"

@UnstableApi
class GuidedMeditationExoPlayer : AppCompatActivity(), Player.Listener,
    CoroutineScope by MainScope() {
    lateinit var videoPlayer: ExoPlayer
    lateinit var audioPlayer: ExoPlayer
    lateinit var audId: String
    lateinit var vidId: String
    lateinit var currentTrackName: String
    private lateinit var videoPlayerView: PlayerView
    private lateinit var audioPlayerView: PlayerView
    private var playbackStateListener: Player.Listener = playbackStateListener()
    private lateinit var playerControlView: PlayerControlView
    //private lateinit var playbackState : PlaybackState
    private lateinit var audioTitle: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var listener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_player)
        audId = intent.getStringExtra("AudioId").toString()
        vidId = intent.getStringExtra("VideoId").toString()
        currentTrackName = intent.getStringExtra("Name").toString()
        audioTitle = findViewById(R.id.guidedMeditationTitle)
        audioTitle.text = currentTrackName
        val backButton: Button = findViewById(R.id.btn_gm_player_back)
        backButton.setBackgroundResource(R.drawable.back_button)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val reference = database.getReference("data/SoundCloud Access Token/date_time_acquired")
        // Read the stored timestamp value
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val storedTimestamp = dataSnapshot.getValue(Long::class.java)
                if (storedTimestamp == null) {
                    // Node is null, populate it with the current date and time
                    val currentDateTime = LocalDateTime.now()
                    val currentTimestamp = currentDateTime.toEpochSecond(java.time.ZoneOffset.UTC)
                    reference.setValue(currentTimestamp)
                } else {
                    // Node is not null, compare the stored date and time plus one hour with the current date and time
                    val storedDateTime =
                        LocalDateTime.ofEpochSecond(storedTimestamp, 0, java.time.ZoneOffset.UTC)
                    val currentDateTime = LocalDateTime.now()
                    val currentDateTimePlusOneHour = currentDateTime.plusHours(1)

                    if (storedDateTime.plusHours(1).isBefore(currentDateTime)) {
                        // The stored date and time plus one hour is in the past
                        // Update the stored date and time
                        val newTimestamp =
                            currentDateTimePlusOneHour.toEpochSecond(java.time.ZoneOffset.UTC)
                        reference.setValue(newTimestamp)

                        // Update the API key here
                        //postAuthorization()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database Error : ", "Firebase DateTime Check Error")
            }
        })
        setupPlayer()

        runBlocking {
            launch {
                delay(2000)
                streamMusic()
            }
            launch {
                delay(1000)
                //getStreamingTrack()
            }
            //googleDriveAuthorization()
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        backButton.setOnClickListener { onStop() }
    }

    private fun googleDriveAuthorization() {
        /*val accManager = GoogleAccountManager(this)
        val myAccount = accManager.getAccountByName(Tui_La_Service_Email)
        val myCredential = GoogleAccountCredential.usingOAuth2(
            this, listOf(DriveScopes.DRIVE, DriveScopes.DRIVE_FILE)
        )
            .setSelectedAccount(myAccount)
            .setSelectedAccountName(Tui_La_Service_Email)
        val googleAccessToken = myCredential.token
        Log.i("GoogleAccessToken",googleAccessToken)*/
        //GoogleSignIn.getLastSignedInAccount(myCredential.context)
        val credentials = GoogleCredential.fromStream(credentialsFile)
            .createScoped(DriveScopes.all())
            .createDelegated(Tui_La_Service_Email)
        val tok = credentials.accessToken
        Log.i("GoogleTok",tok)
        GlobalScope.launch(Dispatchers.IO) {
            val authUrlGoogle = URL("https://accounts.google.com/o/oauth2/v2/auth")
            val authGoogleURLConnection = authUrlGoogle.openConnection() as HttpsURLConnection
            authGoogleURLConnection.requestMethod = "POST"
            authGoogleURLConnection.setRequestProperty("client_id", Tui_La_Service_OAuth2_Client_ID)
            authGoogleURLConnection.setRequestProperty(
                "redirect_uri",
                "https://www.googleapis.com/drive/v3/drives"
            )
            authGoogleURLConnection.setRequestProperty("response_type", "code")
            authGoogleURLConnection.setRequestProperty("scopes", "https://www.googleapis.com/auth/drive")
            authGoogleURLConnection.setRequestProperty("Authorization: Bearer",
                Tui_La_Service_OAuth2_Client_ID
            )
            authGoogleURLConnection.setRequestProperty("Accept", "application/json")
            authGoogleURLConnection.doInput = true
            authGoogleURLConnection.doOutput = true

            //Check if connection is successful
            val responseCodeGoogleAuth = authGoogleURLConnection.responseCode
            if(responseCodeGoogleAuth == HttpURLConnection.HTTP_OK){
                val reader = authGoogleURLConnection.inputStream.bufferedReader()
                val response1 = reader.readText()
                //Log.i("responseGoogleAuthReturnMessage",response1)
            }
            else{
                Log.i("Google Auth Error Code", authGoogleURLConnection.responseCode.toString())
                Log.i("Google Auth Error",authGoogleURLConnection.responseMessage)
            }
        }

    }

    private fun postAuthorization() {
        val uriBuilder = Uri.Builder()
            .appendQueryParameter("client_id", SC_Client_ID)
            .appendQueryParameter("client_secret", SC_Client_Secret)
            .appendQueryParameter("grant_type", "client_credentials")
            .build()

        val params = uriBuilder.toString()
            .replace("?", "") // Remove the "?" from the beginning of the parameter
        val postData = params.toByteArray(StandardCharsets.UTF_8)

        GlobalScope.launch(Dispatchers.IO) {
            val authUrlSC = URL("https://api.soundcloud.com/oauth2/token")
            val httpsURLConnectionSC = authUrlSC.openConnection() as HttpsURLConnection
            httpsURLConnectionSC.requestMethod = "POST"
            httpsURLConnectionSC.setRequestProperty("grant_type", "client_credentials")
            httpsURLConnectionSC.setRequestProperty("client_id", SC_Client_ID)
            httpsURLConnectionSC.setRequestProperty("client_secret", SC_Client_Secret)
            httpsURLConnectionSC.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
            ) // The format of the content we're sending to the server. Confirmed via SoundCloud Public API Specification.
            httpsURLConnectionSC.setRequestProperty(
                "accept",
                "application/json;charset=utf-8"
            ) // The format of response we want to get from the server. Confirmed via SoundCloud Public API Specification.
            httpsURLConnectionSC.doInput = true
            httpsURLConnectionSC.doOutput = true
            val dataOutputStream = DataOutputStream(httpsURLConnectionSC.outputStream)
            dataOutputStream.write(postData)
            dataOutputStream.flush()

            // Check if connection is successful
            val responseCodeSoundCloudAuth = httpsURLConnectionSC.responseCode
            if (responseCodeSoundCloudAuth == HttpURLConnection.HTTP_OK) {
                // Get token database references
                val accessTokenReference =
                    database.getReference("data/SoundCloud Access Token/access_token")
                val refreshTokenReference =
                    database.getReference("data/SoundCloud Access Token/refresh_token")
                // Get returned stream, parse, and place in jsonObject
                val reader = httpsURLConnectionSC.inputStream.bufferedReader()
                val response = reader.readText()
                val jsonObject = JSONObject(response)

                // If need to confirm or double-check the returned access_token and refresh_token,
                // uncomment lines below to print them to the Logcat.
                /*accessToken = jsonObject.getString("access_token")
                refreshToken = jsonObject.getString("refresh_token")
                Log.i("ACCESS TOKEN : ",accessToken)
                Log.i("REFRESH TOKEN : ",refreshToken)*/

                // Reset values of database references
                accessTokenReference.setValue(jsonObject.getString("access_token"))
                refreshTokenReference.setValue(jsonObject.getString("refresh_token"))
                // Close and disconnect
                reader.close()
                httpsURLConnectionSC.disconnect()
            } else {
                Log.e("SoundCloud Auth Connection Error", responseCodeSoundCloudAuth.toString())
                Log.e("ERROR MESSAGE", httpsURLConnectionSC.responseMessage)
                if (responseCodeSoundCloudAuth == 429) { // Cannot ask for more access tokens because hit the rate_limit
                    val reader = httpsURLConnectionSC.inputStream.bufferedReader()
                    val response = reader.readText()
                    Log.d("RATE LIMIT:", response)
                    reader.close()
                    httpsURLConnectionSC.disconnect()
                }
            }

        }
    }

    private fun getStreamingTrack() {
        GlobalScope.launch(Dispatchers.IO) {
            val accessToken = database.getReference("data/SoundCloud Access Token/access_token")
            //val refreshToken = database.getReference("data/SoundCloud Access Token/refresh_token")
            val audioUrlSC = URL("https://api.soundcloud.com/tracks/${trackId}/streams")
            var audioConnectionSC = audioUrlSC.openConnection() as HttpsURLConnection
            audioConnectionSC.requestMethod = "GET"
            audioConnectionSC.setRequestProperty("accept", "application/json; charset=utf-8")
            audioConnectionSC.setRequestProperty("Authorization", "OAuth $accessToken")

            audioConnectionSC.doInput = true
            audioConnectionSC.doOutput = false

            //Check if connection is successful
            val responseCodeAudioConnection = audioConnectionSC.responseCode
            if (responseCodeAudioConnection == HttpURLConnection.HTTP_OK) {
                val response = audioConnectionSC.inputStream.bufferedReader().readText()
                val jsonObject = JSONTokener(response).nextValue() as JSONObject
                httpStreamUrl = jsonObject.getString("http_mp3_128_url")
                audioConnectionSC.disconnect()

                //Uncomment line below if need to print parsed string to the log
                //Log.i("STREAMURL:", httpStreamUrl)

                /*Shouldn't need these, but these are the 3 other urls returned from SoundCloud
                myHls1=jsonObject.getString("hls_mp3_128_url")
                val myHls2=jsonObject.getString("hls_opus_64_url")
                val myPrev=jsonObject.getString("preview_mp3_128_url")*/
            } else {
                Log.e("Track Streaming Error", responseCodeAudioConnection.toString())
                Log.e("ERROR MESSAGE:", audioConnectionSC.responseMessage)
                audioConnectionSC.disconnect()
            }
        }
    }

    private fun streamMusic() {
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        //val progressiveMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            //.createMediaSource(fromUri(httpStreamUrl))
        val googleVidSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            //.createMediaSource(fromUri("https://www.googleapis.com/drive/v3/files/1RMdKtJUQoYA4t1KfycAuq24QizkKemx7?alt=media&key=$Google_Cloud_API_Key"))
            .createMediaSource(fromUri("https://www.googleapis.com/drive/v3/files/$vidId?alt=media&key=$Google_Cloud_API_Key"))
        val googleAudSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            //.createMediaSource(fromUri("https://www.googleapis.com/drive/v3/files/12CflTXe6_8KKRYOYq0Ssx41lpQUsJXip?alt=media&key=$Google_Cloud_API_Key"))
            .createMediaSource(fromUri("https://www.googleapis.com/drive/v3/files/$audId?alt=media&key=$Google_Cloud_API_Key"))
        val audSourceTest = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(fromUri("https://www.googleapis.com/drive/v3/files/1uW0z7nqH6Yn0ADf9FT42kGJdP1Y6X_5P?alt=media&key=$Google_Cloud_API_Key"))
        audioPlayer.setMediaSource(googleAudSource)
        audioPlayer.seekTo(0)
        videoPlayer.setMediaSource(googleVidSource)
        audioPlayer.prepare()
        videoPlayer.prepare()
        audioPlayer.play()
        videoPlayer.play()

    }

    /*override fun onSaveInstanceState(outState:Bundle){
        super.onSaveInstanceState(outState)
        outState.putLong("seekTime",audioPlayer.currentPosition)
        outState.putInt("mediaItem",audioPlayer.currentMediaItemIndex)
    }*/

    @SuppressLint("UnsafeOptInUsageError")
    private fun setupPlayer() {
        val renderersFactory = buildRenderersFactory(this, true)
        val mediaSourceFactory =
            DefaultMediaSourceFactory(getDataSourceFactory(this), DefaultExtractorsFactory())
        val trackSelector = DefaultTrackSelector(this)
        videoPlayer = ExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .setRenderersFactory(renderersFactory)
            .setMediaSourceFactory(mediaSourceFactory)
            .setUseLazyPreparation(true)
            .build().apply {
                trackSelectionParameters =
                    DefaultTrackSelector.Parameters.getDefaults(applicationContext)
                playWhenReady = false
            }
        videoPlayer.repeatMode =
            Player.REPEAT_MODE_ALL // Constantly repeats the video stream over and over for the length of the audio
        videoPlayer.volume = 0f // Mutes any audio coming from the video stream
        videoPlayerView = findViewById(R.id.gm_player_video)
        videoPlayerView.player = videoPlayer
        videoPlayerView.useController = false

        audioPlayer = ExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .setRenderersFactory(renderersFactory)
            .setMediaSourceFactory(mediaSourceFactory)
            .setUseLazyPreparation(true)
            .build().apply {
                trackSelectionParameters =
                    DefaultTrackSelector.Parameters.getDefaults(applicationContext)
                playWhenReady = false
            }
        audioPlayer.seekToDefaultPosition()
        audioPlayerView = findViewById(R.id.gm_player_audio)
        audioPlayerView.player = audioPlayer
        audioPlayerView.setBackgroundColor(Color.TRANSPARENT)
        audioPlayerView.useController = true

        audioPlayer.addListener(
            object: Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    if (events.contains(EVENT_IS_PLAYING_CHANGED)){
                        if (!player.isPlaying){
                            videoPlayer.pause()
                        }
                        else {
                            videoPlayer.play()
                        }
                    }
                }
            }
        )
    }

    private fun buildRenderersFactory(
        context: Context, preferExtensionRenderer: Boolean
    ): RenderersFactory {
        val extensionRendererMode = if (preferExtensionRenderer)
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
        else DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON

        return DefaultRenderersFactory(context)
            .setExtensionRendererMode(extensionRendererMode)
            .setEnableDecoderFallback(true)
    }

    private fun getDataSourceFactory(context: Context): DataSource.Factory =
        DefaultDataSource.Factory(context, getHttpDataSourceFactory())

    private fun getHttpDataSourceFactory(): HttpDataSource.Factory {
        return DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
    }

    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
        if (events.contains(Player.EVENT_IS_PLAYING_CHANGED)) {
            audioPlayer.pause()
            videoPlayer.pause()
        }
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
        /*override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            if (isPlaying) {
                //active playback
            } else {
                // Not playing because playback is paused, ended, suppressed, or the player
                // is buffering, stopped or failed. Check player.playWhenReady,
                // player.playbackState, player.playbackSuppressionReason and
                // player.playerError for details.
            }
        }
*/
        /*override fun onPlayerError(error: PlaybackException) {
            val cause = error.cause
            if (cause is HttpDataSource.HttpDataSourceException) {
                //An HTTP error occurred.
                val httpError = cause
                //It's possible to find out more about the error both by casting and querying the cause
                if (httpError is HttpDataSource.InvalidResponseCodeException) {
                    // Cast to InvalidResponseCodeException and retrieve the response code, message
                    // and headers.
                } else {
                    // Try calling httpError.getCause() to retrieve the underlying cause, although
                    // note that it may be null.
                }
            }
        }*/

    override fun onStop() {
        super.onStop()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        audioPlayer.removeListener(playbackStateListener)
        videoPlayer.removeListener(playbackStateListener)
        videoPlayer.release()
        audioPlayer.release()
        finish()
        val intent = Intent(this,GuidedMeditationActivity::class.java)
        startActivity(intent)
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onMediaMetadataChanged(mediaMetadata)
        audioTitle.text = mediaMetadata.title ?: mediaMetadata.displayTitle ?: "Title not found"
    }
}
