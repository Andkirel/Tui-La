package com.example.tui_la

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tui_la.databinding.FragmentcontainerBinding

class HomeActivity : AppCompatActivity(), ICommunicatorHome {

    private lateinit var binding: FragmentcontainerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentcontainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EmotionsFragment())
                .commit()
        }
    }

    override fun passDataToFragment(uid: String) {
        val fragment = HomeFragment.newInstance(uid)
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
