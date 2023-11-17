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

        view.findViewById<ImageButton>(R.id.Button_Emotions_happy).setOnClickListener { navigateToHomeFragment(uid) }
        view.findViewById<ImageButton>(R.id.Button_Emotions_excited).setOnClickListener { navigateToHomeFragment(uid) }
        view.findViewById<ImageButton>(R.id.Button_Emotions_relaxed).setOnClickListener { navigateToHomeFragment(uid) }
        view.findViewById<ImageButton>(R.id.Button_Emotions_Loved).setOnClickListener { navigateToHomeFragment(uid) }
        view.findViewById<ImageButton>(R.id.Button_Emotions_tired).setOnClickListener { navigateToHomeFragment(uid) }

        // TODO: The following negative emotions will send user to the Prompt for making a journal entry in the future,
        // for now, it will send them to the Home fragment
        view.findViewById<ImageButton>(R.id.Button_Emotions_angry).setOnClickListener { navigateToHomeFragment(uid) }
        view.findViewById<ImageButton>(R.id.Button_Emotions_afraid).setOnClickListener { navigateToHomeFragment(uid) }
        view.findViewById<ImageButton>(R.id.Button_Emotions_stressed).setOnClickListener { navigateToHomeFragment(uid) }
        view.findViewById<ImageButton>(R.id.Button_Emotions_sad).setOnClickListener { navigateToHomeFragment(uid) }

        view.findViewById<Button>(R.id.Button_Emotions_MM).setOnClickListener { navigateToHomeFragment(uid) }
    }

    private fun navigateToHomeFragment(uid: String) {
        (activity as? ICommunicatorHome)?.passDataToFragment(uid)
    }
}
