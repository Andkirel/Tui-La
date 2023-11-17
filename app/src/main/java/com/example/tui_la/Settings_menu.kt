package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tui_la.R
import com.example.tui_la.databinding.FragmentcontainerBinding

class Settings_menu: AppCompatActivity() {
    private lateinit var binding: FragmentcontainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentcontainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Display the EmotionsFragment without adding it to the back stack
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, settingsfragment())
                .commit()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}