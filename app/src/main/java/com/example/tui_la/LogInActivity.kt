package com.example.tui_la

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tui_la.databinding.FragmentcontainerBinding

class LogInActivity : AppCompatActivity(), ICommunicatorLogin {

    private lateinit var binding: FragmentcontainerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentcontainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initially display the LandingFragment when the activity starts
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LandingFragment())
                .commit()
        }
    }


    override fun passDataToFragment(uid: String, secondary: String) {
        val bundle = Bundle()
        bundle.putString("UID", uid)
        bundle.putString("someString", secondary)

        val fragment = WelcomeBackFragment()
        fragment.arguments = bundle

        replaceFragment(fragment)
    }

    fun replaceFragment(fragment: Fragment) {
        // Switch to a different fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
