package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class Badges_Journal_Fragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_badges,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val Jbutton: Button = view.findViewById(R.id.un_editText)
        val BEButton: Button = view.findViewById(R.id.email_editText)
        val GMButton: Button = view.findViewById(R.id.pw_editText)
        val MemButton: Button = view.findViewById(R.id.Button_Createaccount)
        val Backbutton: ImageButton = view.findViewById(R.id.Backbutton_Badges)

        Jbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.coordinatorLayout, Badges_Journal_Fragment())
                .commit()
        }
        BEButton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.coordinatorLayout, Badges_BE_Fragment())
                .commit()
        }
        GMButton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.coordinatorLayout, Badges_GM_Fragment())
                .commit()
        }
        MemButton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.coordinatorLayout, Badges_Mem_Fragment())
                .commit()
        }
        Backbutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.coordinatorLayout, Settings_menu())
                .commit()
        }

    }
}