package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.media3.common.util.UnstableApi

@UnstableApi class HomeFragment : Fragment() {

    private var uid: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_mainscreen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uid = arguments?.getString("UID")

        view.findViewById<Button>(R.id.Button_MainScreen_GM).setOnClickListener {
            navigateToActivity(GuidedMeditationActivity::class.java)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_GB).setOnClickListener {
            navigateToActivity(GuidedBreathingActivityCompose::class.java)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_Jour).setOnClickListener {
            navigateToActivity(JournalWriteActivity::class.java)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_Records).setOnClickListener {
            navigateToActivity(JournalTableActivity::class.java)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_EDU).setOnClickListener {
            //TODO: Create the Education layout and class. Then uncomment line below.
            //navigateToActivity(Education::class.java)
            val toast = Toast.makeText(view.context,"Coming Soon!",Toast.LENGTH_SHORT)
            toast.show()
        }

        view.findViewById<Button>(R.id.Button_MainScreen_Crisis).setOnClickListener {
            navigateToActivity(Csupport::class.java)
        }

        view.findViewById<ImageButton>(R.id.ImageButton_MainMenu_Settings).setOnClickListener {
            navigateToActivity(Settings_menu::class.java)
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(context, activityClass).apply {
            putExtra("UID", uid)
        }
        startActivity(intent)
    }

    // possible redundant function, can remove after checking
    companion object {
        fun newInstance(uid: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("UID", uid)
            fragment.arguments = args
            return fragment
        }
    }
}
