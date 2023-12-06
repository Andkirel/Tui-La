package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class BadgesFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_badges, container, false)

        val Jbutton: Button = view.findViewById(R.id.Button_badges_Journal)
        val BEButton: Button = view.findViewById(R.id.Button_badges_BreathingExercise)
        val GMButton: Button = view.findViewById(R.id.Button_badges_GuidedMeditation)
        val MemButton: Button = view.findViewById(R.id.Button_badges_Membership)
        val Backbutton: ImageButton = view.findViewById(R.id.Backbutton_Badges)

        Jbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_Journal_Fragment())
                .commit()
        }
        BEButton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_BE_Fragment())
                .commit()
        }
        GMButton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_GM_Fragment())
                .commit()
        }
        MemButton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_Mem_Fragment())
                .commit()
        }
        Backbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }
       return view
    }
}