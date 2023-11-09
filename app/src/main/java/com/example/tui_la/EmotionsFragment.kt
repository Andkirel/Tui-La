package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class EmotionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_emotions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = (activity as HomeActivity).intent.getStringExtra("UID") ?: ""


        view.findViewById<Button>(R.id.Button_MainScreen_GB).setOnClickListener {
            navigateToHomeFragment(uid)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_GM).setOnClickListener {
            navigateToHomeFragment(uid)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_Jour).setOnClickListener {
            navigateToHomeFragment(uid)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_Records).setOnClickListener {
            navigateToHomeFragment(uid)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_EDU).setOnClickListener {
            navigateToHomeFragment(uid)
        }

        view.findViewById<Button>(R.id.Button_MainScreen_Crisis).setOnClickListener {
            navigateToHomeFragment(uid)
        }

        view.findViewById<ImageButton>(R.id.ImageButton_MainMenu_Settings).setOnClickListener {
            navigateToHomeFragment(uid)
        }
    }

    private fun navigateToHomeFragment(uid: String) {
        // Assuming your activity implements ICommunicatorHome
        (activity as? ICommunicatorHome)?.passDataToFragment(uid)
    }
}
