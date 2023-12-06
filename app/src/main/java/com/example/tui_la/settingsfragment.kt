package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class settingsfragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_settings, container, false)

        val backbutton: ImageButton = view.findViewById(R.id.Backbutton_settings)
        val accsettingsbutton: Button = view.findViewById(R.id.button_AccontSettings)
        val stickersbutton: Button = view.findViewById(R.id.button_stickers)
        val appsettingsbutton: Button = view.findViewById(R.id.button_Appsettings)
        val apphelpbutton: Button = view.findViewById(R.id.button_apphelp)
        val Logoutbutton: Button = view.findViewById(R.id.Button_Logout)

        backbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EmotionsFragment())
                .commit()
        }
        accsettingsbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_Journal_Fragment())
                .commit()
        }
        stickersbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BadgesFragment())
                .commit()
        }
        appsettingsbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_GM_Fragment())
                .commit()
        }
        apphelpbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_Mem_Fragment())
                .commit()
        }
        Logoutbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LogInFragment())
                .commit()
        }



        return view
    }
}