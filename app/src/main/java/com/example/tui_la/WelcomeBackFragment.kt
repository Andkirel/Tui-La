package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import android.app.Activity

class WelcomeBackFragment : Fragment() {
    private var progressBarStatus = 0
    private var loadInt: Int = 0
    private var uid: String? = null
    private var secondaryString: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_welcome_welcomeback, container, false)

        // Get the UID and secondaryString values from the bundle arguments
        uid = arguments?.getString("UID")
        secondaryString = arguments?.getString("someString")


        // Initialize the ProgressBar and the associated loading thread
        val progressBar = view.findViewById<ProgressBar>(R.id.welcomeScreenProgressBar)

        Thread(Runnable {
            while (progressBarStatus < 100){
                try{
                    loadInt += 25
                    Thread.sleep(1000)
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
                this.progressBarStatus = loadInt
                progressBar.progress = progressBarStatus
            }
        }).start()

        return view
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
