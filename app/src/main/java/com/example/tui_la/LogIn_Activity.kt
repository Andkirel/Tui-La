package com.example.tui_la

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class LogIn_Activity : AppCompatActivity() {

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //    setContentView(R.layout.layout_login)
//
    //    // Initialize UI elements
    //    val CreateAccountButton = findViewById<Button>(R.id.button)
//
    //    // Load the LogIn_Fragment as the default fragment
    //    replaceFragment(LogIn_Fragment())
//
    //    // Set up a listener to switch to CreateAccount_Fragment
    //    CreateAccountButton.setOnClickListener {
    //        replaceFragment(CreateAccount_Fragment())
    //    }
    //}

    private fun replaceFragment(fragment: Fragment) {
        // Replace the current fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.coordinatorLayout, fragment)
            .commit()
    }
}
