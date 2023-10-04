package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.databinding.LayoutLoginactivityBinding
import androidx.fragment.app.Fragment

class LogInActivity : AppCompatActivity(), ICommunicatorLogin {

    private lateinit var binding: LayoutLoginactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutLoginactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initially display the LandingFragment when the activity starts
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.coordinatorLayout, LandingFragment())
                .commit()
        }
    }

    fun replaceFragment(fragment: Fragment) {
        // Switch to a different fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.coordinatorLayout, fragment)
            .commit()
    }

    // Interface for fragment communication
    override fun passDataToFragment(uid: String, secondary: String) {
        val bundle = Bundle()
        bundle.putString("UID", uid)
        bundle.putString("someString", secondary)

        val fragment = WelcomeBackFragment()
        fragment.arguments = bundle

        replaceFragment(fragment)
    }

    fun onUserLoggedIn(uid: String) {
        // Navigate to the HomeActivity once the user is logged in
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("UID", uid)
        startActivity(intent)
        finish()
    }
}
