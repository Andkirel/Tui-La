package com.example.tui_la

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.media3.ui.PlayerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.drive.DriveScopes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
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
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import javax.net.ssl.HttpsURLConnection


//!!DO NOT CHANGE THE BELOW STRINGS. They are necessary to access the SoundCloud API AND Google Drive APIs.!!//
private const val SC_Client_ID = "7mveilIe54NH0aS3LBqBdkJuyo8CCvIu"
private const val SC_Client_Secret = "gdzGo4IE4O3AmipQwvu6TGDE52sKBiJY"
private const val Google_Cloud_API_Key = "AIzaSyA6n51QSYLze9UusF2luIrpk3momzDcm3w"
private const val Tui_La_Service_Email = "tui-la-admin@tui-la-guided-meditation.iam.gserviceaccount.com"
private const val Tui_La_Service_Key_ID = "bb19377f0716e67b35ed30e309f3c51e4a8fee2f"
private const val Tui_La_Service_OAuth2_Client_ID = "117055122084581803157"
private const val Tui_La_Google_OAuth2_Client_ID = "156633646436-olagt48bvdajeu9jvibkh840no7nt53f"
private const val Tui_La_Google_Client_ID_Web_Application = "156633646436-t251lu3n50kpmnstfham4ubj8pd34in9.apps.googleusercontent.com"
private const val Tui_La_Google_Client_Web_Client_Secret = "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"
private const val Tui_La_Google_Authorized_Redirect_Uri = "https://tui-la.com/callback"

var accessToken=""
var refreshToken=""
var httpStreamUrl = ""
var trackId = 1074950746
private const val TAG = "PlayerActivity"

@UnstableApi class GuidedMeditationExoPlayer: AppCompatActivity(), Player.Listener, CoroutineScope by MainScope(){
    lateinit var videoPlayer : ExoPlayer
    lateinit var audioPlayer : ExoPlayer
    private lateinit var videoPlayerView: PlayerView
    private lateinit var audioPlayerView: PlayerView
    private val playbackStateListener: Player.Listener = playbackStateListener()
    private lateinit var audioTitle: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var listener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_player)
        audioTitle = findViewById(R.id.guidedMeditationTitle)
        audioTitle.text = "My Title"
        val backButton : Button = findViewById(R.id.btn_gm_player_back)
        backButton.setBackgroundResource(R.drawable.back_button)

       /* jsonObject.put("client_id",SC_Client_ID)
        jsonObject.put("client_secret",SC_Client_Secret)*/

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
                    val storedDateTime = LocalDateTime.ofEpochSecond(storedTimestamp, 0, java.time.ZoneOffset.UTC)
                    val currentDateTime = LocalDateTime.now()
                    val currentDateTimePlusOneHour = currentDateTime.plusHours(1)

                    if (storedDateTime.plusHours(1).isBefore(currentDateTime)) {
                        // The stored date and time plus one hour is in the past
                        // Update the stored date and time
                        val newTimestamp = currentDateTimePlusOneHour.toEpochSecond(java.time.ZoneOffset.UTC)
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
        runBlocking{
            launch{
                delay(2000)
                streamMusic()
            }
            launch{
                delay(1000)
                getStreamingTrack()
            }
            googleDriveAuthorization()
        }
        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
    private fun googleDriveAuthorization() {
        val accManager = GoogleAccountManager(this)
        val myAccount = accManager.getAccountByName(Tui_La_Service_Email)
        val myCredential = GoogleAccountCredential.usingOAuth2(applicationContext, listOf(DriveScopes.DRIVE,DriveScopes.DRIVE_FILE))
            .setSelectedAccount(myAccount)
        GoogleSignIn.getLastSignedInAccount(myCredential.context)

        GlobalScope.launch(Dispatchers.IO) {
            val authUrlGoogle = URL("https://accounts.google.com/o/oauth2/v2/auth")
            val authGoogleURLConnection = authUrlGoogle.openConnection() as HttpsURLConnection
            authGoogleURLConnection.setRequestProperty("client_id", Tui_La_Service_OAuth2_Client_ID)
            authGoogleURLConnection.setRequestProperty("redirect_uri",
                "https://www.googleapis.com/drive/v3/drives")
            authGoogleURLConnection.setRequestProperty("response_type","code")
            authGoogleURLConnection.setRequestProperty("scopes","https://www.googleapis.com/auth/drive")
            authGoogleURLConnection.setRequestProperty(
                "Authorization: Bearer",
                Tui_La_Service_OAuth2_Client_ID
            )
            authGoogleURLConnection.setRequestProperty("Accept", "application/json")
            authGoogleURLConnection.doInput = true
            authGoogleURLConnection.doOutput = true

            //Check if connection is successful
            val responseCodeGoogleAuth = authGoogleURLConnection.responseCode
            if (responseCodeGoogleAuth == HttpURLConnection.HTTP_OK) {
                //Folder ID: 1S2sGWvIeG3kKtECt200Q92WwgmXnEvZX
                //File ID: 1RMdKtJUQoYA4t1KfycAuq24QizkKemx7
                //val url = URL("https://www.googleapis.com/drive/v3/folders/1S2sGWvIeG3kKtECt200Q92WwgmXnEvZX HTTP/1.1")
                val fileUrl = URL("https://www.googleapis.com/drive/v3/files/1RMdKtJUQoYA4t1KfycAuq24QizkKemx7 HTTP/1.1")

                authGoogleURLConnection.url.sameFile(fileUrl) // Uses the same connection that was confirmed by OAuth2 to access the file.
                authGoogleURLConnection.connect()
                authGoogleURLConnection.requestMethod = "GET"

                val responseCodeGoogleGetFile = authGoogleURLConnection.responseCode
                if (responseCodeGoogleGetFile == HttpURLConnection.HTTP_OK) {
                    val reader = authGoogleURLConnection.inputStream.bufferedReader()
                    val response1 = reader.readText()
                    val jsonObject1 = Gson().toJson(response1).toString()

                    Log.i("Response Message: ",responseCodeGoogleGetFile.toString())
                    reader.close()
                    authGoogleURLConnection.disconnect()
                } else {
                    Log.e("Google Drive Connection Error", responseCodeGoogleGetFile.toString())
                    Log.e("ERROR MESSAGE", authGoogleURLConnection.responseMessage)
                }
            } else {
                Log.e("Authorization Connection Error", responseCodeGoogleAuth.toString())
                Log.e("ERROR MESSAGE", authGoogleURLConnection.responseMessage)
            }
        }
    }

    private fun postAuthorization(){
        val uriBuilder= Uri.Builder()
            .appendQueryParameter("client_id",SC_Client_ID)
            .appendQueryParameter("client_secret",SC_Client_Secret)
            .appendQueryParameter("grant_type","client_credentials")
            .build()

        val params=uriBuilder.toString().replace("?","") // Remove the "?" from the beginning of the parameter
        val postData=params.toByteArray(StandardCharsets.UTF_8)

        GlobalScope.launch(Dispatchers.IO){
            val authUrlSC = URL("https://api.soundcloud.com/oauth2/token")
            val httpsURLConnectionSC = authUrlSC.openConnection() as HttpsURLConnection
            httpsURLConnectionSC.requestMethod="POST"
            httpsURLConnectionSC.setRequestProperty("grant_type","client_credentials")
            httpsURLConnectionSC.setRequestProperty("client_id",SC_Client_ID)
            httpsURLConnectionSC.setRequestProperty("client_secret",SC_Client_Secret)
            httpsURLConnectionSC.setRequestProperty("Content-Type","application/x-www-form-urlencoded") // The format of the content we're sending to the server. Confirmed via SoundCloud Public API Specification.
            httpsURLConnectionSC.setRequestProperty("accept","application/json;charset=utf-8") // The format of response we want to get from the server. Confirmed via SoundCloud Public API Specification.
            httpsURLConnectionSC.doInput=true
            httpsURLConnectionSC.doOutput=true
            val dataOutputStream = DataOutputStream(httpsURLConnectionSC.outputStream)
            dataOutputStream.write(postData)
            dataOutputStream.flush()

            // Check if connection is successful
            val responseCodeSoundCloudAuth= httpsURLConnectionSC.responseCode
            if(responseCodeSoundCloudAuth == HttpURLConnection.HTTP_OK){
                // Get token database references
                val accessTokenReference = database.getReference("data/SoundCloud Access Token/access_token")
                val refreshTokenReference = database.getReference("data/SoundCloud Access Token/refresh_token")
                // Get returned stream, parse, and place in jsonObject
                val reader=httpsURLConnectionSC.inputStream.bufferedReader()
                val response=reader.readText()
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
            }
            else{
                Log.e("SoundCloud Auth Connection Error",responseCodeSoundCloudAuth.toString())
                Log.e("ERROR MESSAGE",httpsURLConnectionSC.responseMessage)
                if(responseCodeSoundCloudAuth==429){ // Cannot ask for more access tokens because hit the rate_limit
                    val reader=httpsURLConnectionSC.inputStream.bufferedReader()
                    val response=reader.readText()
                    Log.d("RATE LIMIT:",response)
                    reader.close()
                    httpsURLConnectionSC.disconnect()
                }
            }

        }
    }

    private fun getStreamingTrack(){
        GlobalScope.launch(Dispatchers.IO){
            val accessToken = database.getReference("data/SoundCloud Access Token/access_token")
            //val refreshToken = database.getReference("data/SoundCloud Access Token/refresh_token")
            val audioUrlSC = URL("https://api.soundcloud.com/tracks/${trackId}/streams")
            var audioConnectionSC = audioUrlSC.openConnection() as HttpsURLConnection
            audioConnectionSC.requestMethod = "GET"
            audioConnectionSC.setRequestProperty("accept","application/json; charset=utf-8")
            audioConnectionSC.setRequestProperty("Authorization","OAuth $accessToken")

            audioConnectionSC.doInput = true
            audioConnectionSC.doOutput = false

            //Check if connection is successful
            val responseCodeAudioConnection = audioConnectionSC.responseCode
            if(responseCodeAudioConnection == HttpURLConnection.HTTP_OK){
                val response= audioConnectionSC.inputStream.bufferedReader().readText()
                val jsonObject= JSONTokener(response).nextValue() as JSONObject
                httpStreamUrl = jsonObject.getString("http_mp3_128_url")
                audioConnectionSC.disconnect()

                //Uncomment line below if need to print parsed string to the log
                //Log.i("STREAMURL:", httpStreamUrl)

                /*Shouldn't need these, but these are the 3 other urls returned from SoundCloud
                myHls1=jsonObject.getString("hls_mp3_128_url")
                val myHls2=jsonObject.getString("hls_opus_64_url")
                val myPrev=jsonObject.getString("preview_mp3_128_url")*/
            }
            else {
                Log.e("Track Streaming Error",responseCodeAudioConnection.toString())
                Log.e("ERROR MESSAGE:",audioConnectionSC.responseMessage)
                audioConnectionSC.disconnect()
            }
        }
    }

    private fun streamMusic(){
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        val progressiveMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(fromUri(httpStreamUrl))
        val googleVidSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(fromUri("https://www.googleapis.com/drive/v3/files/1RMdKtJUQoYA4t1KfycAuq24QizkKemx7?alt=media&key=$Google_Cloud_API_Key"))
        val testMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(fromUri("https://www.googleapis.com/drive/v3/files/12CflTXe6_8KKRYOYq0Ssx41lpQUsJXip?alt=media&key=$Google_Cloud_API_Key"))
        audioPlayer.setMediaSource(testMediaSource)

        videoPlayer.setMediaSource(googleVidSource)

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
        videoPlayer.repeatMode = Player.REPEAT_MODE_ALL // Constantly repeats the video stream over and over for the length of the audio
        videoPlayer.volume = 0f // Mutes any audio coming from the video stream
        videoPlayerView = findViewById(R.id.gm_player_video)
        videoPlayerView.player = videoPlayer
        videoPlayerView.useController = false

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

        /*val audioPlayerListener = audioPlayer.addListener(
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
        )*/

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
