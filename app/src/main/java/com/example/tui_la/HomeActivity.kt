package com.example.tui_la

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tui_la.databinding.FragmentcontainerBinding

class HomeActivity : AppCompatActivity(), ICommunicatorHome {

    private lateinit var binding: FragmentcontainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentcontainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Display the EmotionsFragment without adding it to the back stack
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EmotionsFragment())
                .commit()
        }
    }

    override fun passDataToFragment(uid: String) {
        // Clear the back stack before adding HomeFragment
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val fragment = HomeFragment.newInstance(uid)
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        // If the HomeFragment is the current fragment, do not pop the back stack
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        }
    }
}
