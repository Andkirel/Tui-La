package com.example.tui_la

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.media3.common.util.UnstableApi

@UnstableApi
class settingsfragment: Fragment() {

    private var uid: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_settings, container, false)

        uid = arguments?.getString("UID")

        val backbutton: ImageButton = view.findViewById(R.id.Backbutton_settings)
        val accsettingsbutton: Button = view.findViewById(R.id.button_AccontSettings)
        val stickersbutton: Button = view.findViewById(R.id.button_stickers)
        val appsettingsbutton: Button = view.findViewById(R.id.button_Appsettings)
        val apphelpbutton: Button = view.findViewById(R.id.button_apphelp)
        val Logoutbutton: Button = view.findViewById(R.id.Button_Logout)

        backbutton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        accsettingsbutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_Journal_Fragment())
                .commit()
        }
        stickersbutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BadgesFragment())
                .commit()
        }
        appsettingsbutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_GM_Fragment())
                .commit()
        }
        apphelpbutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_Mem_Fragment())
                .commit()
        }
        Logoutbutton.setOnClickListener {
            // Start Login Activity and clear all previous activities
            val intent = Intent(requireActivity(), LogInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            requireActivity().finish()
        }
        return view
    }
}