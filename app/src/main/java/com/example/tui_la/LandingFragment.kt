package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class LandingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.layout_landing, container, false)

        // Initialize the buttons and set their click listeners
        val signInButton: Button = view.findViewById(R.id.signinButton)
        val createAccountButton: TextView = view.findViewById(R.id.createAccountButton)

        signInButton.setOnClickListener {
            // Replace with LogInFragment
            (activity as? LogInActivity)?.replaceFragment(LogInFragment())
        }

        createAccountButton.setOnClickListener {
            // Replace with CreateAccountFragment
            (activity as? LogInActivity)?.replaceFragment(CreateAccountFragment())
        }

        return view
    }
}
