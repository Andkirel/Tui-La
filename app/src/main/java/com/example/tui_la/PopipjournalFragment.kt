package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class PopipjournalFragment: DialogFragment(R.layout.layout_monthone) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_monthone, container, false)

        val closebutton: ImageButton = view.findViewById(R.id.closebutton_Badges)

        closebutton.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Badges_Journal_Fragment())
                .commit()
        }


        return view

    }

}