package com.example.tui_la

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tui_la.databinding.ActivityMainBinding

class LogIn_Activity : AppCompatActivity(), ICommunicator_Login {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI elements
        val createAccountButton: Button = findViewById(R.id.ca_button)  // Make sure you provide the correct ID

        // Load the LogIn_Fragment as the default fragment
        replaceFragment(LogIn_Fragment())

        // Set up a listener to switch to CreateAccount_Fragment
        createAccountButton.setOnClickListener {
            replaceFragment(CreateAccount_Fragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // Replace the current fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.coordinatorLayout, fragment)
            .commit()
    }

    // Implement interface for fragment communication
    override fun passDataToFragment(uid: String, secondary: String) {
        val bundle = Bundle()
        bundle.putString("UID", uid)
        bundle.putString("someString", secondary)

        val fragment = WelcomeBack_Fragment
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
