package com.example.tui_la

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeBackFragment : Fragment() {
    private var progressBarStatus = 0
    private var uid: String? = null
    private var secondaryString: String? = null
    private val database = FirebaseDatabase.getInstance().reference

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_welcome_welcomeback, container, false)

        // Get the UID and secondaryString values from the bundle arguments
        uid = arguments?.getString("UID")
        secondaryString = arguments?.getString("someString")

        progressBar = view.findViewById(R.id.welcomeScreenProgressBar)
        val usernameTextView = view.findViewById<TextView>(R.id.usernameText)

        // Fetch username from Firebase
        uid?.let {
            database.child("users").child(it).child("username").get().addOnSuccessListener { snapshot ->
                val username = snapshot.value.toString()
                usernameTextView.text = username
            }
        }

        // Start the loading coroutine
        CoroutineScope(Dispatchers.Main).launch {
            delayLoading()
            launchHowFeelingScreen(uid ?: "", secondaryString ?: "")
        }

        return view
    }

    private suspend fun delayLoading() {
        for (i in 0 until 4) {
            delay(1000) // simulate loading for 1 second
            progressBarStatus += 25
            progressBar.progress = progressBarStatus
        }
    }

    private fun launchHowFeelingScreen(uid: String, secondary: String) {
        val intent = Intent(requireActivity(), HomeActivity::class.java).apply {
            putExtra("UID", uid)
            putExtra("someString", secondary)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        requireActivity().setResult(Activity.RESULT_OK)
        requireActivity().finish()
    }
}
